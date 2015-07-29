package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;
import org.pircbotx.PircBotX;

import java.util.Scanner;

import static com.techcavern.wavetactdb.Tables.NETWORKS;

@ConCMD
public class Network extends ConsoleCommand {

    public Network() {
        super(GeneralUtils.toArray("network"), "network (+/-)[networkname] (Property) (Value)", "Creates, modifies or removes network configurations");
    }

    @Override
    public void onCommand(String command, String[] args, CommandIO commandIO) throws Exception {
        Scanner input = new Scanner(commandIO.getInputStream());
        if (args[0].startsWith("+")) {
            Record network = DatabaseUtils.getNetwork(args[0].replaceFirst("\\+", ""));
            boolean viewonly = false;
            if (args.length < 3) {
                viewonly = true;
            }
            boolean isSuccess = false;
            if (network != null) {
                switch (args[1].toLowerCase()) {
                    case "channels":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.CHANNELS));
                        else {
                            network.setValue(NETWORKS.CHANNELS, GeneralUtils.buildMessage(2, args.length, args));
                            isSuccess = true;
                        }
                        break;
                    case "server":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.SERVER));
                        else {
                            network.setValue(NETWORKS.SERVER, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "port":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.PORT));
                        else {
                            network.setValue(NETWORKS.PORT, Integer.parseInt(args[2]));
                            isSuccess = true;
                        }
                        break;
                    case "nick":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.NICK));
                        else {
                            network.setValue(NETWORKS.NICK, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "netadmins":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.NETWORKADMINS));
                        else {
                            network.setValue(NETWORKS.NETWORKADMINS, GeneralUtils.buildMessage(2, args.length, args));
                            isSuccess = true;
                        }
                        break;
                    case "bindhost":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.BINDHOST));
                        else {
                            network.setValue(NETWORKS.BINDHOST, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "serverpass":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.SERVERPASS));
                        else if (args[2].equalsIgnoreCase("null")) {
                            network.setValue(NETWORKS.SERVERPASS, null);
                            isSuccess = true;
                        } else {
                            network.setValue(NETWORKS.SERVERPASS, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "nickservcommand":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.NICKSERVCOMMAND));
                        else if (args[2].equalsIgnoreCase("null")) {
                            network.setValue(NETWORKS.NICKSERVCOMMAND, null);
                            isSuccess = true;
                        } else {
                            network.setValue(NETWORKS.NICKSERVCOMMAND, GeneralUtils.buildMessage(2, args.length, args));
                            isSuccess = true;
                        }
                        break;
                    case "nickservnick":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.NICKSERVNICK));
                        else {
                            network.setValue(NETWORKS.NICKSERVNICK, args[2]);
                            isSuccess = true;
                        }
                        break;
                    case "netadminaccess":
                        if (viewonly)
                            commandIO.getPrintStream().println(network.getValue(NETWORKS.NETWORKADMINACCESS));
                        else {
                            network.setValue(NETWORKS.NETWORKADMINACCESS, Boolean.valueOf(args[2]));
                            isSuccess = true;
                        }
                        break;
                    default:
                        commandIO.getPrintStream().println("Failed to parse property");
                }
                if (isSuccess) {
                    DatabaseUtils.updateNetwork(network);
                    commandIO.getPrintStream().println("Property Modified");
                }

            } else {
                commandIO.getPrintStream().println("Network does not exist");
            }

        } else if (args[0].startsWith("-")) {
            DatabaseUtils.removeNetwork(args[0].replaceFirst("\\-", ""));
            DatabaseUtils.removeChannelUserPropertyByNetwork(args[0].replaceFirst("\\-", ""));
            DatabaseUtils.removeNetworkUserPropertyByNetwork(args[0].replaceFirst("\\-", ""));
            PircBotX network = IRCUtils.getNetworkByNetworkName(args[0].replaceFirst("\\-", ""));
            Registry.networks.inverse().remove(network);
            Registry.messageQueue.remove(network);
            Registry.authedUsers.remove(network);
            Registry.whoisEventCache.remove(network);
            Registry.lastWhois.remove(network);
            Registry.lastLeftChannel.remove(network);
            network.stopBotReconnect();
            network.sendIRC().quitServer();
            commandIO.getPrintStream().println("network removed");
        } else {
            if (DatabaseUtils.getNetwork(args[0]) != null) {
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
            commandIO.getPrintStream().print("AuthType (nickserv/account/hostmask): ");
            String authtype = input.nextLine();
            commandIO.getPrintStream().print("Auto Allow Network Operators NetAdmin Level Access? (True/False): ");
            boolean netadminaccess = Boolean.valueOf(input.nextLine());
            commandIO.getPrintStream().print("Network Admins: (account1, account2, etc) (Press enter to ignore): ");
            String netadmins = "";
            String netadminsinput = input.nextLine();
            if (!netadminsinput.isEmpty())
                netadmins = netadminsinput;
            DatabaseUtils.addNetwork(name, port, server, nick, channels, bindhost, netadminaccess, netadmins, authtype, nickservcommand, serverpass, nickservnick);
            PircBotX network = ConfigUtils.createNetwork(serverpass, nick, server, port, bindhost, name);
            Registry.networks.put(name, network);
            LoadUtils.addMessageQueue(network);
            Registry.WaveTact.addNetwork(network);
        }
    }
}
