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
        addCommands(Constants.ChanFounderCommands, ChanFounderCMD.class);
        addCommands(Constants.ChanHalfOpCommands, ChanHOPCMD.class);
        addCommands(Constants.ChanOpCommands, ChanOPCMD.class);
        addCommands(Constants.ChanOwnerCommands, ChanOWNCMD.class);
        addCommands(Constants.ControllerCommands, ConCMD.class);
        addCommands(Constants.GenericCommands, GenCMD.class);
        addCommands(Constants.TrustedCommands, TruCMD.class);
        addCommands(Constants.NetAdminCommands, NAdmCMD.class);
    }

    public static void initializeCommandlines() {
        addCommandLines(Constants.CommandLines, CMDLine.class);
    }

    public static void parseCommandLineArguments(String[] args) {
        boolean invalid = true;
        if (args.length < 1) {
            com.techcavern.wavetact.commandline.Help c = new com.techcavern.wavetact.commandline.Help();
            c.doAction(args);
        }
        for (CommandLine c : Constants.CommandLineArguments) {
            for (String b : c.getArgument()) {
                for (String s : args) {

                    if (s.equalsIgnoreCase("-" + b)) {
                        c.doAction(args);
                        invalid = false;
                    }
                }
            }
        }
        for (CommandLine c : Constants.CommandLines) {
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
        Set<Class<?>> classes = Constants.wavetactreflection.getTypesAnnotatedWith(cl);
        for (Class<?> clss : classes) {
            try {
                list.add(((GenericCommand) clss.newInstance()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addCommandLines(List<CommandLine> list, Class<? extends Annotation> cl) {
        Set<Class<?>> classes = Constants.wavetactreflection.getTypesAnnotatedWith(cl);
        for (Class<?> clss : classes) {
            try {
                list.add(((CommandLine) clss.newInstance()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerAttacks() {
        Constants.Attacks.add(new FunObject("sends a 53 inch monitor flying at $*", false, null));
        Constants.Attacks.add(new FunObject("shoots a rocket at $*", false, null));
        Constants.Attacks.add(new FunObject("punches $* right in the crotch", false, null));
        Constants.Attacks.add(new FunObject("places a bomb near $* set for 2 seconds", true, "BANG!"));
        Constants.Attacks.add(new FunObject("packs $* up and ships it off to another galaxy", false, null));
        Constants.Attacks.add(new FunObject("eats $* up for breakfast", false, null));
        Constants.Attacks.add(new FunObject("sends a flying desk at $*", false, null));
        Constants.Attacks.add(new FunObject("swallows $* whole", false, null));
        Constants.Attacks.add(new FunObject("ties $* up and feeds it to a shark", false, null));
        Constants.Attacks.add(new FunObject("runs over $*", true, "HEY! WATCH WHERE YOU'RE GOING!"));
        Constants.Attacks.add(new FunObject("throws a racket at $*", false, null));
    }

    public static void registerQuiets() {
        Constants.QuietBans.put("u", "b ~q:");
        Constants.QuietBans.put("c", "q ");
        Constants.QuietBans.put("i", "b m:");
    }

    public static void registerEightball() {
        Constants.Eightball.add("Hmm.. not today");
        Constants.Eightball.add("YES!");
        Constants.Eightball.add("Maybe");
        Constants.Eightball.add("Nope.");
        Constants.Eightball.add("Sources say no.");
        Constants.Eightball.add("Definitely");
        Constants.Eightball.add("I have my doubts");
        Constants.Eightball.add("Signs say yes");
        Constants.Eightball.add("Cannot predict now");
        Constants.Eightball.add("It is certain");
        Constants.Eightball.add("Outlook decent");
        Constants.Eightball.add("Very doubtful");
        Constants.Eightball.add("Perhaps now is not a good time to tell you");
        Constants.Eightball.add("Concentrate and ask again");
        Constants.Eightball.add("Forget about it");
        Constants.Eightball.add("Don't count on it");
    }

    public static void registerCommandList() {
        for (GenericCommand command : Constants.GenericCommands) {
            Constants.GenericListCommands.add(command.getCommand());
            Constants.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Constants.TrustedCommands) {
            Constants.TrustedListCommands.add(command.getCommand());
            Constants.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Constants.ChanHalfOpCommands) {
            Constants.ChanHalfOpListCommands.add(command.getCommand());
            Constants.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Constants.ChanOpCommands) {
            Constants.ChanOpListCommands.add(command.getCommand());
            Constants.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Constants.ChanOwnerCommands) {
            Constants.ChanOwnerListCommands.add(command.getCommand());
            Constants.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Constants.ChanFounderCommands) {
            Constants.ChanFounderListCommands.add(command.getCommand());
            Constants.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Constants.NetAdminCommands) {
            Constants.NetAdminListCommands.add(command.getCommand());
            Constants.AllListCommands.add(command.getCommand());
        }
        for (GenericCommand command : Constants.ControllerCommands) {
            Constants.ControllerListCommands.add(command.getCommand());
            Constants.AllListCommands.add(command.getCommand());
        }
    }
}
