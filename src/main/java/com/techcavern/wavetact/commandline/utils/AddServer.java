package com.techcavern.wavetact.commandline.utils;

import com.techcavern.wavetact.annot.CMDLine;
import com.techcavern.wavetact.utils.GeneralUtils;
import com.techcavern.wavetact.utils.fileUtils.Configuration;
import com.techcavern.wavetact.utils.objects.CommandLine;

import java.io.File;
import java.util.Scanner;

public class AddServer extends CommandLine {
    @CMDLine
    public AddServer() {
        super(GeneralUtils.toArray("addserver a"), "Create a server configuration file", false);
    }

    @Override
    public void doAction(String[] args) {
        boolean loop = true;
        while (loop) {
            Scanner input = new Scanner(System.in);
            System.out.print("Server name: ");
            String name = input.nextLine();
            Configuration config = new Configuration(new File("servers/", name + ".server"));
            config.set("name", name);
            System.out.print("Server host: ");
            config.set("server", input.nextLine());
            System.out.print("Server nick: ");
            config.set("nick", input.nextLine());
            System.out.print("Channels(#chan1, #chan2): ");
            config.set("channels", input.nextLine());
            System.out.print("Nickserv Pass(False to disable): ");
            config.set("nickserv", input.nextLine());
            System.out.print("Command Prefix: ");
            config.set("prefix", input.nextLine());
            System.out.print("AuthType (nickserv/account): ");
            config.set("authtype", input.nextLine());
            config.save();
            System.out.print("Add another? [y/n]");
            char ans = input.next().charAt(0);
            if (ans == 'n')
                loop = false;
        }
        System.exit(0);
    }
}
