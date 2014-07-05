WaveTact [![Build Status](https://travis-ci.org/TechCavern/WaveTact.svg?branch=master)](https://travis-ci.org/TechCavern/WaveTact)
--------
An IRC bot that is based upon PircBotX, and sits in #techcavern on the following networks:
- ElectroCode (irc.electrocode.net)
- EsperNET (irc.esper.net)
- Xertion (irc.xertion.org)
- Overdrive (irc.overdrive.pw)
- Freenode (irc.freenode.net)
- ObsidianIRC (irc.obsidianirc.net)

WaveTact is under the DWTFPL

Building
========
- Run `gradle jar`
- Libraries will be downloaded and packed into the JAR
- You will find the JAR in PROJECT_ROOT/build/libs/

Permissions
===========
-By default, everyone is given their permissions based on channel status. Voiced is representative of trusted
-By default, authors are given trusted status
-In src/main/java/com/techcavern/wavetact/utils/GeneralRegistry.java, you will find Controller and Controller hostmasks. Controller hostmasks are a failover in case the network you are on does not run Atheme or fails to show "is Logged in as" in /whois. Controllers have access to ALL commands and override all other permissions. Be careful as to who you give this to.

Running
=======
- Run `java -jar WaveTact.jar`
- WaveTact help is access by the -h or -help parameter
- Make configuration by using the -addserver or -a parameter
- If you wish to populate the custom commands database with some prechosen commands use -addbasiccommands ONCE.
