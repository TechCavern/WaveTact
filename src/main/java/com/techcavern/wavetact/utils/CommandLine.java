package com.techcavern.wavetact.utils;

import java.io.File;

import java.util.Scanner;

public class CommandLine
{

    public static void parseCommandLineArguments(String[] args)
    {
        for (String s : args)
        {
            if (s.equals("--help") || s.equals("-h"))
            {
                System.out.println("Help");
                System.out.println("-a or --addserver - Add a server to connect to");
                System.out.println("-h or --help - Show this help screen");
                System.exit(0);
            }
            else if (s.equals("--addserver") || s.equals("-a"))
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
            else
            {
                System.out.println("Invalid argument: " + s);
                System.out.println("Use -h or --help for help");
                System.exit(0);
            }
        }
    }

}
