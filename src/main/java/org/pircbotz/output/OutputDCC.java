package org.pircbotz.output;

import java.net.InetAddress;
import org.pircbotz.PircBotZ;
import org.pircbotz.dcc.DccHandler;
import org.apache.commons.lang3.StringUtils;

public class OutputDCC {

    private final PircBotZ bot;

    public OutputDCC(PircBotZ bot) {
        this.bot = bot;
    }

    void dcc(String target, String service, Object... parameters) {
        bot.sendIRC().ctcpCommand(target, StringUtils.join(new Object[]{"DCC", service, parameters}, " "));
    }

    public void fileRequest(String target, String filename, InetAddress senderAddress, int senderPort, long filesize) {
        dcc(target, "SEND", filename, DccHandler.addressToInteger(senderAddress), senderPort, filesize);
    }

    public void fileResumeRequest(String target, String filename, int senderPort, long position) {
        dcc(target, "RESUME", filename, senderPort, position);
    }

    public void fileResumeAccept(String target, String filename, int senderPort, long position) {
        dcc(target, "ACCEPT", filename, senderPort, position);
    }

    public void filePassiveRequest(String target, String filename, InetAddress senderAddress, long filesize, String transferToken) {
        dcc(target, "SEND", filename, DccHandler.addressToInteger(senderAddress), 0, filesize, transferToken);
    }

    public void filePassiveAccept(String target, String filename, InetAddress receiverAddress, int receiverPort, long filesize, String transferToken) {
        dcc(target, "SEND", filename, DccHandler.addressToInteger(receiverAddress), receiverPort, filesize, transferToken);
    }

    public void filePassiveResumeRequest(String target, String filename, long position, String transferToken) {
        dcc(target, "RESUME", filename, 0, position, transferToken);
    }

    public void filePassiveResumeAccept(String target, String filename, long position, String transferToken) {
        dcc(target, "ACCEPT", filename, 0, position, transferToken);
    }

    public void chatRequest(String target, InetAddress address, int port) {
        dcc(target, "CHAT", "chat", DccHandler.addressToInteger(address), port);
    }

    public void chatPassiveRequest(String target, InetAddress address, String chatToken) {
        dcc(target, "CHAT", "chat", DccHandler.addressToInteger(address), 0, chatToken);
    }

    public void chatPassiveAccept(String target, InetAddress address, int port, String chatToken) {
        dcc(target, "CHAT", "chat", DccHandler.addressToInteger(address), port, chatToken);
    }
}
