package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Record;
import org.pircbotx.PircBotX;

import java.util.Arrays;
import java.util.Scanner;

import static com.techcavern.wavetactdb.Tables.SERVERS;

@ConCMD
public class Network extends ConsoleCommand {

    public Network() {
        super(GeneralUtils.toArray("network"), "network (+/-)[networkname] (Property) (Value)", "Creates, modifies or removes network configurations");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception {
        Scanner input = new Scanner(commandIO.getInputStream());
        if (args[0].startsWith("+")) {
            Record network = DatabaseUtils.getServer(args[0].replaceFirst("\\+", ""));
            boolean viewonly = false;
            if (args.length < 3) {
                viewonly = true;
            }
            boolean isSuccess = false;
            if (network != null) {
                switch (args[1].toLowerCase()) {
                    case "channels":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.CHANNELS));
                        else {
                            network.setValue(SERVERS.CHANNELS, GeneralUtils.buildMessage(2, args.length, args));
                            isSuccess = true;
                        }
                        break;
                    case "server":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.SERVER));
                        else {
                            network.setValue(SERVERS.SERVER, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "port":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.PORT));
                        else {
                            network.setValue(SERVERS.PORT, Integer.parseInt(args[2]));
                            isSuccess = true;
                        }
                        break;
                    case "nick":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.NICK));
                        else {
                            network.setValue(SERVERS.NICK, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "netadmin":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.NETWORKADMINS));
                        else {
                            network.setValue(SERVERS.NETWORKADMINS, GeneralUtils.buildMessage(2, args.length, args));
                            isSuccess = true;
                        }
                        break;
                    case "bindhost":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.BINDHOST));
                        else {
                            network.setValue(SERVERS.BINDHOST, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "serverpass":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.SERVERPASS));
                        else {
                            network.setValue(SERVERS.SERVERPASS, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "nickservcommand":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.NICKSERVCOMMAND));
                        else {
                            network.setValue(SERVERS.NICKSERVCOMMAND, GeneralUtils.buildMessage(2, args.length, args));
                            isSuccess = true;
                        }
                        break;
                    case "nickservnick":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.NICKSERVNICK));
                        else {
                            network.setValue(SERVERS.NICKSERVNICK, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "authtype":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.AUTHTYPE));
                        else {
                            network.setValue(SERVERS.AUTHTYPE, args[2]);
                            Registry.AuthedUsers.clear();
                            isSuccess = true;
                        }
                        break;
                    case "netadminaccess":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(SERVERS.NETWORKADMINACCESS));
                        else {
                            network.setValue(SERVERS.NETWORKADMINACCESS, Boolean.valueOf(args[2]));
                            isSuccess = true;
                        }
                        break;
                    default:
                        commandIO.getPrintStream().println("Failed to parse property");
                }
                if (isSuccess) {
                    DatabaseUtils.updateServer(network);
                    commandIO.getPrintStream().println("Property Modified");
                }

            }

        } else if (args[0].startsWith("-")) {
            DatabaseUtils.removeServer(args[0].replaceFirst("\\-", ""));
            PircBotX network = IRCUtils.getBotByNetworkName(args[0].replaceFirst("\\-", ""));
            for (NetProperty e : Registry.NetworkName) {
                if (e.getNetwork().equals(network)) {
                    Registry.NetworkName.remove(e);
                    network.stopBotReconnect();
                    network.sendIRC().quitServer();
                    commandIO.getPrintStream().println("network removed");
                    return;
                }
            }
        } else {
            if (DatabaseUtils.getServer(args[0]) != null) {
                commandIO.getPrintStream().println("network already exists");
                return;
            }
            commandIO.getPrintStream().println("Adding " + args[0]);
            String name = args[0];
            commandIO.getPrintStream().print("Server host: ");
            String server = input.nextLine();
            commandIO.getPrintStream().print("Server port (Press enter to use default): ");
            int port = 6667;
            String portinput = input.nextLine();
            if (!portinput.isEmpty())
                port = Integer.parseInt(portinput);
            commandIO.getPrintStream().print("Server nick: ");
            String nick = input.nextLine();
            commandIO.getPrintStream().print("Channels (#chan1, #chan2, etc): ");
            String channels = input.nextLine();
            commandIO.getPrintStream().print("Server Pass (Press enter to ignore): ");
            String serverpass = null;
            String serverpassinput = input.nextLine();
            if (!serverpassinput.isEmpty())
                serverpass = serverpassinput;
            commandIO.getPrintStream().print("NickServ Nick (Press enter to use default - NickServ): ");
            String nickservnick = null;
            String nickservnickinput = input.nextLine();
            if (!nickservnickinput.isEmpty())
                nickservnick = nickservnickinput;
            commandIO.getPrintStream().print("NickServ Command (eg. IDENTIFY PASSWORD1234, Press enter to ignore): ");
            String nickservcommand = null;
            String nickservcommandinput = input.nextLine();
            if (!nickservcommandinput.isEmpty())
                nickservcommand = nickservcommandinput;
            commandIO.getPrintStream().print("Bindhost (Press enter to use default): ");
            String bindhost = null;
            String bindhostinput = input.nextLine();
            if (!bindhostinput.isEmpty())
                bindhost = bindhostinput;
            commandIO.getPrintStream().print("AuthType (nickserv/account/nick): ");
            String authtype = input.nextLine();
            commandIO.getPrintStream().print("Auto Allow Network Operators NetAdmin Level Access? (True/False): ");
            boolean netadminaccess = Boolean.valueOf(input.nextLine());
            commandIO.getPrintStream().print("Network Admins: (account1, account2, etc) (Press enter to ignore): ");
            String netadmins = "";
            String netadminsinput = input.nextLine();
            if (!netadminsinput.isEmpty())
                netadmins = netadminsinput;
            DatabaseUtils.addServer(name, port, server, nick, channels, bindhost, netadminaccess, netadmins, authtype, nickservcommand,serverpass, nickservnick);
            PircBotX network = ConfigUtils.createNetwork(serverpass, Arrays.asList(StringUtils.split(channels, ", ")), nick, server, port, bindhost, name);
            Registry.WaveTact.addNetwork(network);
            Registry.NetworkName.add(new NetProperty(name, network));
            LoadUtils.addMessageQueue(network);
        }
    }
}
