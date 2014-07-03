package com.techcavern.wavetact.utils.commandline;

import com.techcavern.wavetact.objects.CommandLineObject;
import com.techcavern.wavetact.utils.Configuration;

import java.io.File;
import java.util.Scanner;

public class AddServer implements CommandLineObject
{

    public String getShortArgument()
    {
        return "a";
    }

    public String getLongArgument()
    {
        return "addserver";
    }

    public String getHelpString()
    {
        return "Create a server configuration file";
    }

    public void doAction()
    {
        boolean loop = true;
        while (loop)
        {
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
            System.out.print("Nickserv Pass(empty for none): ");
            config.set("nickserv", input.nextLine());
            System.out.print("Command Prefix: ");
            config.set("prefix", input.nextLine()); 
            config.save();
            System.out.print("Add another? [y/n]");
            char ans = input.next().charAt(0);
            if (ans == 'n')
                loop = false;
        }
        System.exit(0);
    }

}
