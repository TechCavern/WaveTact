package com.techcavern.wavetact.utils;

import com.google.common.io.Files;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.FunObject;
import com.techcavern.wavetact.objects.IRCCommand;
import org.flywaydb.core.Flyway;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.util.Set;

public class LoadUtils {

    public static void initiateDatabaseConnection() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:./db.sqlite", null, null);
        flyway.migrate();


        System.err.println("Getting connection...");
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:./db.sqlite");

        System.err.println("Creating DSLContext...");
        Registry.WaveTactDB = DSL.using(conn, SQLDialect.SQLITE);
    }

    public static void registerIRCCommands() {
        Set<Class<?>> classes = Registry.wavetactreflection.getTypesAnnotatedWith(IRCCMD.class);
        for (Class<?> clss : classes) {
            try {
                Registry.IRCCommands.add(((IRCCommand) clss.newInstance()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerConsoleCommands() {
        Set<Class<?>> classes = Registry.wavetactreflection.getTypesAnnotatedWith(IRCCMD.class);
        for (Class<?> clss : classes) {
            try {
                Registry.ConsoleCommands.add(((ConsoleCommand) clss.newInstance()));
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

    public static void addDir(String s) throws IOException {
        try {
            // This enables the java.library.path to be modified at runtime
            // From a Sun engineer at http://forums.sun.com/thread.jspa?threadID=707176
            //
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[]) field.get(null);
            for (int i = 0; i < paths.length; i++) {
                if (s.equals(paths[i])) {
                    return;
                }
            }
            String[] tmp = new String[paths.length + 1];
            System.arraycopy(paths, 0, tmp, 0, paths.length);
            tmp[paths.length] = s;
            field.set(null, tmp);
            System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + s);
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            throw new IOException("Failed to get field handle to set library path");
        }
    }

    public static void unpackNatives() throws IOException {
        System.err.println("Extracting natives...");
        File tempDir = new File("./libs");
        tempDir.mkdirs();
        tempDir.mkdir();
        Scanner files = new Scanner(LoadUtils.class.getClassLoader().getResourceAsStream("files.txt"));
        while (files.hasNextLine()) {
            String filename = files.nextLine();
            if (filename.length() == 0)
                continue;
            System.err.println("Processing: " + filename);
            File targetFile = new File(tempDir.getAbsolutePath() + "/" + filename);
            if (!targetFile.exists()) {
                InputStream source = LoadUtils.class.getResourceAsStream("/" + filename);
                byte[] buffer = new byte[source.available()];
                source.read(buffer);
                Files.write(buffer, targetFile);
            } else
                System.err.println("-> Skipping. File exists.");
        }
        System.err.println("Patching java.library.path...");
        addDir(tempDir.getAbsolutePath());
    }
}
