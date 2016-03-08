package com.techcavern.wavetact.utils;

import com.techcavern.wavetact.annot.ConCMD;
import com.techcavern.wavetact.annot.IRCCMD;
import com.techcavern.wavetact.objects.ConsoleCommand;
import com.techcavern.wavetact.objects.IRCCommand;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.pircbotx.Colors;
import org.pircbotx.PircBotX;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.techcavern.wavetactdb.Tables.*;

public class LoadUtils {

    public static void initiateDatabaseConnection() throws Exception {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:./db.sqlite", null, null);
        flyway.setValidateOnMigrate(false);
        flyway.migrate();
        System.err.println("Getting connection...");
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:./db.sqlite");
        System.err.println("Creating DSLContext...");
        Registry.wavetactDB = DSL.using(conn, SQLDialect.SQLITE);
    }

    public static void registerIRCCommands() {
        Set<Class<?>> classes = Registry.wavetactReflection.getTypesAnnotatedWith(IRCCMD.class);
        for (Class<?> clss : classes) {
            try {
                IRCCommand command = (IRCCommand) clss.newInstance();
                Registry.ircCommandList.add(command);
                String[] comids = command.getCommandID();
                for (String comid : comids) {
                    Registry.ircCommands.put(comid, command);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void migrate() {
        if (DatabaseUtils.getConfig("CURRENT_ITERATION") != null && Integer.parseInt(DatabaseUtils.getConfig("CURRENT_ITERATION").getValue(CONFIG.VALUE)) >= Registry.CURRENT_ITERATION) {
            return;
        } else {
            DatabaseUtils.removeConfig("CURRENT_ITERATION");
            DatabaseUtils.addConfig("CURRENT_ITERATION", String.valueOf(Registry.CURRENT_ITERATION));
            Registry.ircCommands.keySet().stream().forEach(commandid -> {
                DatabaseUtils.removeCustomCommand(commandid);
            });
            for (Record netRecord : DatabaseUtils.getNetworks()) {
                if(netRecord.getValue(NETWORKS.SSL) == null){
                    netRecord.setValue(NETWORKS.SSL, false);
                    DatabaseUtils.updateNetwork(netRecord);
                }
            }
        }
    }

    public static void registerConsoleCommands() {
        Set<Class<?>> classes = Registry.wavetactReflection.getTypesAnnotatedWith(ConCMD.class);
        for (Class<?> clss : classes) {
            try {
                ConsoleCommand command = (ConsoleCommand) clss.newInstance();
                Registry.consoleCommandList.add(command);
                String[] comids = command.getCommandID();
                for (String comid : comids) {
                    Registry.consoleCommands.put(comid, command);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerAttacks() {
        Registry.attacks.add("sends a 53 inch monitor flying at $*");
        Registry.attacks.add("shoots a rocket at $*");
        Registry.attacks.add("punches $* right in the crotch");
        Registry.attacks.add("packs $* up and ships it off to another galaxy");
        Registry.attacks.add("eats $* up for breakfast");
        Registry.attacks.add("sends a flying desk at $*");
        Registry.attacks.add("swallows $* whole");
        Registry.attacks.add("ties $* up and feeds it to a shark");
        Registry.attacks.add("runs over $* with a car");
        Registry.attacks.add("throws a racket at $*");
        Registry.attacks.add("gobbles up $*");
        Registry.attacks.add("throws a 2000 pound object at $*");
        Registry.attacks.add("starts throwing punches at $*");
        Registry.attacks.add("sends a flying dragon at $*");
        Registry.attacks.add("takes over $*'s computers and blasts porn at full volume");
        Registry.attacks.add("packs $* up and ships them off to Apple");
        Registry.attacks.add("hands $* off to Lord Voldemort");
        Registry.attacks.add("hands $* off to a pack of a wolves");
        Registry.attacks.add("hands $* off to a herd of centaurs");
        Registry.attacks.add("drops $* off to a 2000 kilometer cliff");
        Registry.attacks.add("flies $* out into the middle of nowhere");
        Registry.attacks.add("hunts $* down with a gun");
        Registry.attacks.add("slaps $* around with a large trout");
        Registry.attacks.add("throws iphones at $*");
        Registry.attacks.add("fires missile at $*");
        Registry.attacks.add("puts $* in a rocket and sends them off to pluto");
        Registry.attacks.add("forcefeeds $* a plate of poisoned beef");
        Registry.attacks.add("mind controls $* to marry Dolores Umbridge");
        Registry.attacks.add("throws poorly written code at $*");
        Registry.attacks.add("throws knives at $*");
        Registry.attacks.add("throws various objects at $*");
        Registry.attacks.add("throws rocks at $*");
        Registry.attacks.add("throws grenades at $*");
        Registry.attacks.add("throws IE6 at $*");
        Registry.attacks.add("throws axes at $*");
        Registry.attacks.add("throws evil things at $*");
        Registry.attacks.add("throws netsplits at $*");
        Registry.attacks.add("throws hammers at $*");
        Registry.attacks.add("throws spears at $*");
        Registry.attacks.add("throws spikes at $*");
        Registry.attacks.add("throws $* into a burning building");
        Registry.attacks.add("throws sharp things at $*");
        Registry.attacks.add("throws moldy bread at $*");
        Registry.attacks.add("throws mojibake at $*");
        Registry.attacks.add("throws floppy disks at $*");
        Registry.attacks.add("throws nails at $*");
        Registry.attacks.add("throws burning planets at $*");
        Registry.attacks.add("throws thorns at $*");
        Registry.attacks.add("throws skulls at $*");
        Registry.attacks.add("throws a fresh, unboxed copy of Windows Me at $*");
        Registry.attacks.add("casts fire at $*");
        Registry.attacks.add("casts ice at $*");
        Registry.attacks.add("casts death at $*");
        Registry.attacks.add("casts " + Colors.BOLD + "DEATH" + Colors.BOLD + " at $*");
        Registry.attacks.add("casts poison at $*");
        Registry.attacks.add("casts stupid at $*");
        Registry.attacks.add("attacks $* with knives");
        Registry.attacks.add("attacks $* with idiots from #freenode");
        Registry.attacks.add("attacks $* with an army of trolls");
        Registry.attacks.add("attacks $* with oper abuse");
        Registry.attacks.add("attacks $* with confusingly bad english");
        Registry.attacks.add("attacks $* with Windows Me");
        Registry.attacks.add("attacks $* with Quicktime for Windows");
        Registry.attacks.add("attacks $* with ???");
        Registry.attacks.add("attacks $* with segmentation faults");
        Registry.attacks.add("attacks $* with relentless spyware");
        Registry.attacks.add("attacks $* with NSA spies");
        Registry.attacks.add("attacks $* with tracking devices");
        Registry.attacks.add("attacks $* with a botnet");
    }

    public static void registerEightball() {
        Registry.eightBall.add("Hmm.. not today");
        Registry.eightBall.add("YES!");
        Registry.eightBall.add("Maybe");
        Registry.eightBall.add("Nope.");
        Registry.eightBall.add("Sources say no.");
        Registry.eightBall.add("Definitely");
        Registry.eightBall.add("I have my doubts");
        Registry.eightBall.add("Signs say yes");
        Registry.eightBall.add("Cannot predict now");
        Registry.eightBall.add("It is certain");
        Registry.eightBall.add("Sure");
        Registry.eightBall.add("Outlook decent");
        Registry.eightBall.add("Very doubtful");
        Registry.eightBall.add("Perhaps now is not a good time to tell you");
        Registry.eightBall.add("Concentrate and ask again");
        Registry.eightBall.add("Forget about it");
        Registry.eightBall.add("Don't count on it");
    }

    public static void addDir(String s) throws IOException {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[]) field.get(null);
            for (String path : paths) {
                if (s.equals(path)) {
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

    public static void registerCharReplacements() {
        Registry.charReplacements.put("a", "á");
        Registry.charReplacements.put("b", "ḃ");
        Registry.charReplacements.put("c", "ƈ");
        Registry.charReplacements.put("d", "ḋ");
        Registry.charReplacements.put("f", "ḟ");
        Registry.charReplacements.put("g", "ǧ");
        Registry.charReplacements.put("h", "ĥ");
        Registry.charReplacements.put("j", "ĵ");
        Registry.charReplacements.put("k", "ķ");
        Registry.charReplacements.put("l", "ĺ");
        Registry.charReplacements.put("m", "ṁ");
        Registry.charReplacements.put("n", "ǹ");
        Registry.charReplacements.put("p", "ṗ");
        Registry.charReplacements.put("q", "ɋ");
        Registry.charReplacements.put("r", "ȓ");
        Registry.charReplacements.put("s", "ș");
        Registry.charReplacements.put("t", "ț");
        Registry.charReplacements.put("w", "ŵ");
        Registry.charReplacements.put("z", "ƶ");
        Registry.charReplacements.put("B", "Ɓ");
        Registry.charReplacements.put("C", "Ƈ");
        Registry.charReplacements.put("D", "Ḋ");
        Registry.charReplacements.put("F", "Ḟ");
        Registry.charReplacements.put("G", "Ǵ");
        Registry.charReplacements.put("H", "Ĥ");
        Registry.charReplacements.put("J", "Ĵ");
        Registry.charReplacements.put("K", "Ƙ");
        Registry.charReplacements.put("L", "Ŀ");
        Registry.charReplacements.put("M", "Ṁ");
        Registry.charReplacements.put("N", "Ǹ");
        Registry.charReplacements.put("P", "Ṗ");
        Registry.charReplacements.put("Q", "Ɋ");
        Registry.charReplacements.put("R", "Ɍ");
        Registry.charReplacements.put("S", "Ṡ");
        Registry.charReplacements.put("T", "Ṫ");
        Registry.charReplacements.put("W", "Ẅ");
        Registry.charReplacements.put("Z", "Ƶ");
        Registry.charReplacements.put("e", "é");
        Registry.charReplacements.put("i", "í");
        Registry.charReplacements.put("o", "ó");
        Registry.charReplacements.put("u", "ú");
        Registry.charReplacements.put("y", "ý");
        Registry.charReplacements.put("A", "Á");
        Registry.charReplacements.put("E", "É");
        Registry.charReplacements.put("I", "Í");
        Registry.charReplacements.put("O", "Ó");
        Registry.charReplacements.put("U", "Ú");
        Registry.charReplacements.put("Y", "Ý");

    }
    public static void initializeMessageQueue() {
        Iterator iterator = Registry.networks.inverse().keySet().iterator();
        while (iterator.hasNext()) {
            PircBotX network = (PircBotX) iterator.next();
            Registry.whoisEventCache.put(network, new ConcurrentHashMap<>());
            Registry.authedUsers.put(network, new ConcurrentHashMap<>());
            Registry.messageQueue.put(network, new LinkedList<>());
            Registry.lastLeftChannel.put(network, "");
            Registry.lastWhois.put(network, "");
            class MessageQueue implements Runnable {
                @Override
                public void run() {
                    while (IRCUtils.getNetworkNameByNetwork(network) != null) {
                        try {
                            if (Registry.messageQueue.get(network).size() > 0) {
                                String Message = Registry.messageQueue.get(network).remove();
                                network.sendRaw().rawLine(Message);
                                TimeUnit.MILLISECONDS.sleep(900);
                            }
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (Exception e) {
                        }
                    }
                }

            }
            Registry.threadPool.execute(new MessageQueue());
        }

    }

    public static void addMessageQueue(PircBotX network) {
        Registry.whoisEventCache.put(network, new ConcurrentHashMap<>());
        Registry.authedUsers.put(network, new ConcurrentHashMap<>());
        Registry.messageQueue.put(network, new LinkedList<>());
        Registry.lastLeftChannel.put(network, "");
        Registry.lastWhois.put(network, "");
        class MessageQueue implements Runnable {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException c) {
                }
                while (IRCUtils.getNetworkNameByNetwork(network) != null) {
                    try {
                        if (Registry.messageQueue.get(network).size() > 0) {
                            String Message = Registry.messageQueue.get(network).remove();
                            network.sendRaw().rawLine(Message);
                            TimeUnit.MILLISECONDS.sleep(900);
                        }
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }

        }
        Registry.threadPool.execute(new MessageQueue());
    }


    public static void initializeAutoFlushWhoisCache() {
        class flushWhoisCache implements Runnable {
            @Override
            public void run() {
                try {
                    TimeUnit.HOURS.sleep(1);
                    Registry.whoisEventCache.keySet().stream().forEach(net -> {
                        Registry.whoisEventCache.get(net).clear();
                    });
                } catch (Exception e) {

                }
            }

        }
        Registry.threadPool.execute(new flushWhoisCache());
    }

    public static void initializeBanQueue() {
        class BanQueue implements Runnable {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException c) {
                }
                while (true) {
                    try {
                        for (Record banRecord : DatabaseUtils.getBans()) {
                            try {
                                if (System.currentTimeMillis() >= banRecord.getValue(BANS.TIME) + banRecord.getValue(BANS.INIT)) {
                                    PircBotX networkObject = IRCUtils.getNetworkByNetworkName(banRecord.getValue(BANS.NETWORK));
                                    if (!banRecord.getValue(BANS.HOSTMASK).isEmpty() && banRecord.getValue(BANS.HOSTMASK) != null)
                                        IRCUtils.setMode(IRCUtils.getChannelbyName(networkObject, banRecord.getValue(BANS.CHANNEL)), networkObject, "-" + banRecord.getValue(BANS.PROPERTY), banRecord.getValue(BANS.HOSTMASK));
                                    DatabaseUtils.removeBan(banRecord.getValue(BANS.NETWORK), banRecord.getValue(BANS.CHANNEL), banRecord.getValue(BANS.HOSTMASK), banRecord.getValue(BANS.ISMUTE));
                                }
                            } catch (IllegalArgumentException | NullPointerException e) {
                                // ignored
                            }
                        }
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Registry.threadPool.execute(new BanQueue());
    }
    public static void initializeVoiceQueue() {
        class VoiceQueue implements Runnable {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(30);
                } catch (InterruptedException c) {
                }
                while (true) {
                    try {
                        String network = "moo";
                        String channel = "moo";
                        HashSet<String> moo = new HashSet<>();
                        for (Record voiceRecord : DatabaseUtils.getVoices()) {
                            try {
                                if (System.currentTimeMillis() >= voiceRecord.getValue(VOICES.TIME)) {
                                    network = voiceRecord.getValue(VOICES.NETWORK);
                                    channel = voiceRecord.getValue(VOICES.CHANNEL);
                                    DatabaseUtils.removeVoice(voiceRecord.getValue(VOICES.NETWORK), voiceRecord.getValue(VOICES.CHANNEL), voiceRecord.getValue(VOICES.NICK));
                                    moo.add(voiceRecord.getValue(VOICES.NICK));
                                    break;
                                }
                            } catch (IllegalArgumentException | NullPointerException e) {
                                // ignored
                            }
                        }
                        for (Record voiceRecord : DatabaseUtils.getVoices(network,channel)) {
                            try {
                                if (System.currentTimeMillis() >= voiceRecord.getValue(VOICES.TIME)) {
                                    if(moo.size() < 4) {
                                        DatabaseUtils.removeVoice(voiceRecord.getValue(VOICES.NETWORK), voiceRecord.getValue(VOICES.CHANNEL), voiceRecord.getValue(VOICES.NICK));
                                        moo.add(voiceRecord.getValue(VOICES.NICK));
                                    }else{
                                        break;
                                    }
                                }
                            } catch (IllegalArgumentException | NullPointerException e) {
                                // ignored
                            }
                        }
                        if(IRCUtils.getChannelbyName(IRCUtils.getNetworkByNetworkName(network),channel) != null)
                        IRCUtils.setMode(IRCUtils.getChannelbyName(IRCUtils.getNetworkByNetworkName(network),channel),IRCUtils.getNetworkByNetworkName(network),"-vvvv", StringUtils.join(moo, " "));
                        TimeUnit.SECONDS.sleep(120);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Registry.threadPool.execute(new VoiceQueue());
    }
}
