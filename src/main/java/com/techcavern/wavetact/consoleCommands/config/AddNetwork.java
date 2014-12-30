package com.techcavern.wavetact.consoleCommands.config;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.objects.CommandIO;
import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.objects.ConsoleCommand;

import java.io.File;
import java.util.Scanner;

@CMDLine
public class AddNetwork extends ConsoleCommand {

    public AddNetwork() {
        super(GeneralUtils.toArray("addnetwork anet"),"addnetwork", "Create a network configuration file");
    }

    @Override
    public void onCommand(String[] args, CommandIO commandIO) {
        boolean loop = true;
        while (loop) {
            new File("servers/").mkdir();
            Scanner input = new Scanner(commandIO.getInputStream());
            commandIO.getPrintStream().print("Server name: ");
            String name = input.nextLine();
            Configuration config = new Configuration(new File("servers/", name + ".server"));
            config.set("name", name);
            commandIO.getPrintStream().print("Server host: ");
            config.set("server", input.nextLine());
            commandIO.getPrintStream().print("Server Port(Enter 6667 to use Default): ");
            config.set("port", input.nextLine());
            commandIO.getPrintStream().print("Server nick: ");
            config.set("nick", input.nextLine());
            commandIO.getPrintStream().print("Channels(#chan1, #chan2): ");
            config.set("channels", input.nextLine());
            commandIO.getPrintStream().print("Nickserv Pass(Enter False to disable): ");
            config.set("nickserv", input.nextLine());
            commandIO.getPrintStream().print("Bindhost(Enter None to use Default): ");
            config.set("bindhost", input.nextLine());
            commandIO.getPrintStream().print("Command Prefix: ");
            config.set("prefix", input.nextLine());
            commandIO.getPrintStream().print("AuthType (nickserv/account): ");
            config.set("authtype", input.nextLine());
            commandIO.getPrintStream().print("Auto Allow Network Operators NetAdmin Level Access? (True/False): ");
            config.set("netadminaccess", input.nextLine());
            config.save();
            commandIO.getPrintStream().print("Add another? [y/n]");
            char ans = input.next().charAt(0);
            if (ans == 'n')
                loop = false;
        }
    }
}
