package org.pircbotz.output;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.pircbotz.PircBotZ;
import org.pircbotz.Utils;
import org.apache.commons.lang3.Validate;

public class OutputRaw {

    private final PircBotZ bot;
    private final ReentrantLock writeLock = new ReentrantLock(true);
    private final Condition writeNowCondition = writeLock.newCondition();
    private final long delayNanos;
    private long lastSentLine = 0;

    public OutputRaw(PircBotZ bot) {
        this.bot = bot;
        this.delayNanos = bot.getConfiguration().getMessageDelay() * 1000000;
    }

    public OutputRaw(PircBotZ bot, long delayNanos) {
        this.bot = bot;
        this.delayNanos = delayNanos;
    }

    public void rawLine(String line) {
        Validate.notNull(line, "Line cannot be null");
        if (line == null) {
            throw new NullPointerException("Cannot send null messages to server");
        }
        if (!bot.isConnected()) {
            throw new RuntimeException("Not connected to server");
        }
        writeLock.lock();
        try {
            long curNanos = System.nanoTime();
            while (lastSentLine + delayNanos > curNanos) {
                writeNowCondition.await(lastSentLine + delayNanos - curNanos, TimeUnit.NANOSECONDS);
                curNanos = System.nanoTime();
            }
            Utils.sendRawLineToServer(bot, line);
            lastSentLine = System.nanoTime();
        } catch (InterruptedException e) {
            throw new RuntimeException("Couldn't pause thread for message delay", e);
        } finally {
            writeLock.unlock();
        }
    }

    public void rawLineNow(String line) {
        rawLineNow(line, false);
    }

    void rawLineNow(String line, boolean resetDelay) {
        Validate.notNull(line, "Line cannot be null");
        if (!bot.isConnected()) {
            throw new RuntimeException("Not connected to server");
        }
        writeLock.lock();
        try {
            Utils.sendRawLineToServer(bot, line);
            lastSentLine = System.nanoTime();
            if (resetDelay) {
                writeNowCondition.signalAll();
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void rawLineSplit(String prefix, String message) {
        rawLineSplit(prefix, message, "");
    }

    public void rawLineSplit(String prefix, String message, String suffix) {
        Validate.notNull(prefix, "Prefix cannot be null");
        Validate.notNull(message, "Message cannot be null");
        Validate.notNull(suffix, "Suffix cannot be null");
        String finalMessage = prefix + message + suffix;
        int realMaxLineLength = bot.getConfiguration().getMaxLineLength() - 2;
        if (!bot.getConfiguration().isAutoSplitMessage() || finalMessage.length() < realMaxLineLength) {
            rawLine(finalMessage);
            return;
        }
        int maxMessageLength = realMaxLineLength - (prefix + suffix).length();
        int iterations = (int) Math.ceil(message.length() / (double) maxMessageLength);
        for (int i = 0; i < iterations; i++) {
            int endPoint = (i != iterations - 1) ? ((i + 1) * maxMessageLength) : message.length();
            String curMessagePart = prefix + message.substring(i * maxMessageLength, endPoint) + suffix;
            rawLine(curMessagePart);
        }
    }

    public int getOutgoingQueueSize() {
        return writeLock.getHoldCount();
    }
}
