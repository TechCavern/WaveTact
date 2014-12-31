package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.NetProperty;
import com.techcavern.wavetact.utils.*;
import org.jooq.Record;
import org.jooq.tools.StringUtils;
import org.pircbotx.PircBotX;

import java.util.Arrays;
import java.util.Scanner;

import static com.techcavern.wavetactdb.Tables.SERVERS;

@ConCMD
public class Network extends ConsoleCommand {

    public Network() {
        super(GeneralUtils.toArray("network"), "network (+/-)[networkname] (Property) (Value)", "Create, modifies or removes a network configuration");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) throws Exception{
        Scanner input = new Scanner(commandIO.getInputStream());
        if (args[0].startsWith("+")) {
            Record network = databaseUtils.getServer(args[0].replaceFirst("\\+", ""));
            boolean viewonly = false;
            if(args.length <3 ){
                viewonly = true;
            }
            switch (args[1].toLowerCase()) {
                case "name":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.NAME));
                    else
                    network.setValue(SERVERS.NAME, args[2]);
                    break;
                case "server":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.SERVER));
                    else
                    network.setValue(SERVERS.SERVER, args[2]);
                    break;
                case "port":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.PORT));
                    else
                    network.setValue(SERVERS.PORT, Integer.parseInt(args[2]));
                    break;
                case "nick":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.NICK));
                    else
                    network.setValue(SERVERS.NICK, args[2]);
                    break;
                case "nickserv":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.NICKSERV));
                    else
                    network.setValue(SERVERS.NICKSERV, args[2]);
                    break;
                case "controllers":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.CONTROLLERS));
                    else
                        network.setValue(SERVERS.CONTROLLERS, args[2]);
                    break;
                case "netadmin":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.NETWORKADMINS));
                    else
                        network.setValue(SERVERS.NETWORKADMINS, args[2]);
                    break;
                case "bindhost":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.BINDHOST));
                    else
                    network.setValue(SERVERS.BINDHOST, args[2]);
                    break;
                case "authtype":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.AUTHTYPE));
                    else
                    network.setValue(SERVERS.AUTHTYPE, args[2]);
                    break;
                case "netadminaccess":
                    if(viewonly)
                        commandIO.getPrintStream().println(network.getValue(SERVERS.NETWORKADMINACCESS));
                    else
                    network.setValue(SERVERS.NETWORKADMINACCESS, Boolean.valueOf(args[2]));
                    break;
                default:
                    commandIO.getPrintStream().println("Failed to parse property");
            }

        } else if (args[0].startsWith("-")) {
            databaseUtils.deleteServer(args[0].replaceFirst("\\-", ""));
            IRCUtils.getBotByNetworkName(args[0]).stopBotReconnect();
        } else {
            commandIO.getPrintStream().println("Adding " + args[0]);
            String name = args[0];
            commandIO.getPrintStream().print("Server host: ");
            String server = input.nextLine();
            commandIO.getPrintStream().print("Server port (Press enter to use default): ");
            int port = Integer.parseInt(input.nextLine());
            commandIO.getPrintStream().print("Server nick: ");
            String nick = input.nextLine();
            commandIO.getPrintStream().print("Channels (#chan1, #chan2, etc): ");
            String channels = input.nextLine();
            commandIO.getPrintStream().print("Nickserv Pass (Press enter to ignore): ");
            String nickserv = input.nextLine();
            commandIO.getPrintStream().print("Bindhost (Press enter to use default): ");
            String bindhost = input.nextLine();
            commandIO.getPrintStream().print("Controllers: (account1, account2, etc)");
            String controllers = input.nextLine();
            commandIO.getPrintStream().print("AuthType (nickserv/account/nick): ");
            String authtype = input.nextLine();
            commandIO.getPrintStream().print("Auto Allow Network Operators NetAdmin Level Access? (True/False): ");
            boolean netadminaccess = Boolean.valueOf(input.nextLine());
            commandIO.getPrintStream().print("Network Admins: (account1, account2, etc) (Press enter to ignore): ");
            String netadmins = input.nextLine();
            databaseUtils.addServer(name, port, server, nick, channels, nickserv, bindhost, netadminaccess, netadmins, authtype, controllers);
            PircBotX network = ConfigUtils.createNetwork(nickserv, Arrays.asList(StringUtils.split(channels, ", ")), nick, server, port, bindhost, name);
            Registry.WaveTact.addNetwork(network);
            network.startBot();
            Registry.NetworkName.add(new NetProperty(name, network));
        }
    }
}
