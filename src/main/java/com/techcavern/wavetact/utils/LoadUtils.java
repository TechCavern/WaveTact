package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.annot.*;
import com.techcavern.wavetact.utils.objects.CommandLine;
import com.techcavern.wavetact.utils.objects.FunObject;
import com.techcavern.wavetact.utils.objects.GenericCommand;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public class LoadUtils {

    public static void registerCommands() {
        addCommands(Registry.ChanAdminCommands, ChanAdminCMD.class);
        addCommands(Registry.ChanHalfOpCommands, ChanHOPCMD.class);
        addCommands(Registry.ChanOpCommands, ChanOPCMD.class);
        addCommands(Registry.ChanOwnOpCommands, ChanOwnOpCMD.class);
        addCommands(Registry.ControllerCommands, ConCMD.class);
        addCommands(Registry.GenericCommands, GenCMD.class);
        addCommands(Registry.TrustedCommands, TruCMD.class);
        addCommands(Registry.NetAdminCommands, NAdmCMD.class);
    }

    public static void initializeCommandlines() {
        addCommandLines(Registry.CommandLines, CMDLine.class);
    }

    public static void parseCommandLineArguments(String[] args) {
        boolean invalid = true;
        if (args.length < 1) {
            com.techcavern.wavetact.commandline.Help c = new com.techcavern.wavetact.commandline.Help();
            c.doAction(args);
        }
        for (CommandLine c : Registry.CommandLineArguments) {
            for (String b : c.getArgument()) {
                for (String s : args) {

                    if (s.equalsIgnoreCase("-" + b)) {
                        c.doAction(args);
                        invalid = false;
                    }
                }
            }
        }
        for (CommandLine c : Registry.CommandLines) {
            for (String b : c.getArgument()) {

                if (args[0].equalsIgnoreCase("-" + b)) {
                    c.doAction(args);
                    invalid = false;


                }
            }
        }
        if (invalid) System.exit(0);
    }

    public static void addCommands(List<GenericCommand> list, Class<? extends Annotation> cl) {
        Set<Class<?>> classes = Registry.wavetactreflection.getTypesAnnotatedWith(cl);
        for (Class<?> clss : classes) {
            try {
                list.add(((GenericCommand) clss.newInstance()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addCommandLines(List<CommandLine> list, Class<? extends Annotation> cl) {
        Set<Class<?>> classes = Registry.wavetactreflection.getTypesAnnotatedWith(cl);
        for (Class<?> clss : classes) {
            try {
                list.add(((CommandLine) clss.newInstance()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerAttacks() {
        Registry.Attacks.add(new FunObject("sends a 53 inch monitor flying at $*", false, null));
        Registry.Attacks.add(new FunObject("shoots a rocket at $*", false, null));
        Registry.Attacks.add(new FunObject("punches $* right in the crotch", false, null));
        Registry.Attacks.add(new FunObject("places a bomb near $* set for 2 seconds", true, "BANG!"));
        Registry.Attacks.add(new FunObject("packs $* up and ships it off to another galaxy", false, null));
        Registry.Attacks.add(new FunObject("eats $* up for breakfast", false, null));
        Registry.Attacks.add(new FunObject("sends a flying desk at $*", false, null));
        Registry.Attacks.add(new FunObject("swallows $* whole", false, null));
        Registry.Attacks.add(new FunObject("ties $* up and feeds it to a shark", false, null));
        Registry.Attacks.add(new FunObject("runs over $*", true, "HEY! WATCH WHERE YOU'RE GOING!"));
        Registry.Attacks.add(new FunObject("throws a racket at $*", false, null));
    }

    public static void registerQuiets() {
        Registry.QuietBans.put("u", "b ~q:");
        Registry.QuietBans.put("c", "q ");
        Registry.QuietBans.put("i", "b m:");
    }

    public static void registerEightball() {
        Registry.Eightball.add("Hmm.. not today");
        Registry.Eightball.add("YES!");
        Registry.Eightball.add("Maybe");
        Registry.Eightball.add("Nope.");
        Registry.Eightball.add("Sources say no.");
        Registry.Eightball.add("Definitely");
        Registry.Eightball.add("I have my doubts");
        Registry.Eightball.add("Signs say yes");
        Registry.Eightball.add("Cannot predict now");
        Registry.Eightball.add("It is certain");
        Registry.Eightball.add("Sure");
        Registry.Eightball.add("Outlook decent");
        Registry.Eightball.add("Very doubtful");
        Registry.Eightball.add("Perhaps now is not a good time to tell you");
        Registry.Eightball.add("Concentrate and ask again");
        Registry.Eightball.add("Forget about it");
        Registry.Eightball.add("Don't count on it");
    }

    public static void registerCommandList() {
        for (GenericCommand command : Registry.GenericCommands) {
            Registry.GenericListCommands.add(command.getCommand());
            Registry.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Registry.TrustedCommands) {
            Registry.TrustedListCommands.add(command.getCommand());
            Registry.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Registry.ChanHalfOpCommands) {
            Registry.ChanHalfOpListCommands.add(command.getCommand());
            Registry.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Registry.ChanOpCommands) {
            Registry.ChanOpListCommands.add(command.getCommand());
            Registry.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Registry.ChanOwnOpCommands) {
            Registry.ChanOwnOpListCommands.add(command.getCommand());
            Registry.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Registry.ChanAdminCommands) {
            Registry.ChanAdminListCommands.add(command.getCommand());
            Registry.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Registry.NetAdminCommands) {
            Registry.NetAdminListCommands.add(command.getCommand());
            Registry.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Registry.ControllerCommands) {
            Registry.ControllerListCommands.add(command.getCommand());
            Registry.AllListCommands.add(command.getCommand());
        }
    }
}
