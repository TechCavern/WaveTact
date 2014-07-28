package org.pircbotz;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import org.pircbotz.dcc.DccHandler;
import org.pircbotz.dcc.ReceiveChat;
import org.pircbotz.dcc.ReceiveFileTransfer;
import org.pircbotz.dcc.SendChat;
import org.pircbotz.dcc.SendFileTransfer;
import org.pircbotz.output.OutputCAP;
import org.pircbotz.output.OutputChannel;
import org.pircbotz.output.OutputDCC;
import org.pircbotz.output.OutputIRC;
import org.pircbotz.output.OutputRaw;
import org.pircbotz.output.OutputUser;

public abstract class BotFactory {

    public abstract UserChannelDao<PircBotZ, User, Channel> createUserChannelDao(PircBotZ bot);

    public OutputRaw createOutputRaw(PircBotZ bot) {
        return new OutputRaw(bot);
    }

    public OutputCAP createOutputCAP(PircBotZ bot) {
        return new OutputCAP(bot);
    }

    public OutputIRC createOutputIRC(PircBotZ bot) {
        return new OutputIRC(bot);
    }

    public OutputDCC createOutputDCC(PircBotZ bot) {
        return new OutputDCC(bot);
    }

    public OutputChannel createOutputChannel(PircBotZ bot, Channel channel) {
        return new OutputChannel(bot, channel);
    }

    public OutputUser createOutputUser(PircBotZ bot, User user) {
        return new OutputUser(bot, user);
    }

    public InputParser createInputParser(PircBotZ bot) {
        return new InputParser(bot);
    }

    public DccHandler createDccHandler(PircBotZ bot) {
        return new DccHandler(bot);
    }

    public SendChat createSendChat(PircBotZ bot, User user, Socket socket) throws IOException {
        return new SendChat(user, socket, bot.getConfiguration().getEncoding());
    }

    public ReceiveChat createReceiveChat(PircBotZ bot, User user, Socket socket) throws IOException {
        return new ReceiveChat(user, socket, bot.getConfiguration().getEncoding());
    }

    public SendFileTransfer createSendFileTransfer(PircBotZ bot, Socket socket, User user, File file, long startPosition) {
        return new SendFileTransfer(bot.getConfiguration(), socket, user, file, startPosition);
    }

    public ReceiveFileTransfer createReceiveFileTransfer(PircBotZ bot, Socket socket, User user, File file, long startPosition) {
        return new ReceiveFileTransfer(bot.getConfiguration(), socket, user, file, startPosition);
    }

    public ServerInfo createServerInfo(PircBotZ bot) {
        return new ServerInfo(bot);
    }

    public abstract User createUser(PircBotZ bot, String nick);

    public abstract Channel createChannel(PircBotZ bot, String name);

    public static class DefaultBotFactory extends BotFactory {

        @Override
        public UserChannelDao<PircBotZ, User, Channel> createUserChannelDao(PircBotZ bot) {
            return new UserChannelDao<>(bot, bot.getConfiguration().getBotFactory(), PircBotZ.class, User.class, Channel.class);
        }

        @Override
        public OutputChannel createOutputChannel(PircBotZ bot, Channel channel) {
            return new OutputChannel(bot, channel);
        }

        @Override
        public OutputUser createOutputUser(PircBotZ bot, User user) {
            return new OutputUser(bot, user);
        }

        @Override
        public InputParser createInputParser(PircBotZ bot) {
            return new InputParser(bot);
        }

        @Override
        public DccHandler createDccHandler(PircBotZ bot) {
            return new DccHandler(bot);
        }

        @Override
        public SendChat createSendChat(PircBotZ bot, User user, Socket socket) throws IOException {
            return new SendChat(user, socket, bot.getConfiguration().getEncoding());
        }

        @Override
        public ReceiveChat createReceiveChat(PircBotZ bot, User user, Socket socket) throws IOException {
            return new ReceiveChat(user, socket, bot.getConfiguration().getEncoding());
        }

        @Override
        public SendFileTransfer createSendFileTransfer(PircBotZ bot, Socket socket, User user, File file, long startPosition) {
            return new SendFileTransfer(bot.getConfiguration(), socket, user, file, startPosition);
        }

        @Override
        public ReceiveFileTransfer createReceiveFileTransfer(PircBotZ bot, Socket socket, User user, File file, long startPosition) {
            return new ReceiveFileTransfer(bot.getConfiguration(), socket, user, file, startPosition);
        }

        @Override
        public ServerInfo createServerInfo(PircBotZ bot) {
            return new ServerInfo(bot);
        }

        @Override
        public User createUser(PircBotZ bot, String nick) {
            return new User(bot, bot.getUserChannelDao(), nick);
        }

        @Override
        public Channel createChannel(PircBotZ bot, String name) {
            return new Channel(bot, bot.getUserChannelDao(), name);
        }
    }
}
