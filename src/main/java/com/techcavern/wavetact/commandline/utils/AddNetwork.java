package com.techcavern.wavetact.commandline.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.utils.objects.CommandLine;

import java.io.File;
import java.util.Scanner;

@CMDLine
public class AddNetwork extends CommandLine {

    public AddNetwork() {
        super(GeneralUtils.toArray("addnetwork anet"), "Create a network configuration file", false);
    }

    @Override
    public void doAction(String[] args) {
        boolean loop = true;
        while (loop) {
            new File("servers/").mkdir();
            Scanner input = new Scanner(System.in);
            System.out.print("Server name: ");
            String name = input.nextLine();
            Configuration config = new Configuration(new File("servers/", name + ".server"));
            config.set("name", name);
            System.out.print("Server host: ");
            config.set("server", input.nextLine());
            System.out.print("Server Port(Enter 6667 to use Default): ");
            config.set("port", input.nextLine());
            System.out.print("Server nick: ");
            config.set("nick", input.nextLine());
            System.out.print("Channels(#chan1, #chan2): ");
            config.set("channels", input.nextLine());
            System.out.print("Nickserv Pass(Enter False to disable): ");
            config.set("nickserv", input.nextLine());
            System.out.print("Bindhost(Enter None to use Default): ");
            config.set("bindhost", input.nextLine());
            System.out.print("Command Prefix: ");
            config.set("prefix", input.nextLine());
            System.out.print("AuthType (nickserv/account): ");
            config.set("authtype", input.nextLine());
            System.out.print("Auto Allow Network Operators NetAdmin Level Access? (True/False): ");
            config.set("netadminaccess", input.nextLine());
            config.save();
            System.out.print("Add another? [y/n]");
            char ans = input.next().charAt(0);
            if (ans == 'n')
                loop = false;
        }
        System.exit(0);
    }
}
