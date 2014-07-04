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

Running
=======
- Run `java -jar WaveTact.jar`
- WaveTact help is access by the -h or -help parameter
- Make configuration by using the -addserver or -a parameter
- If you wish to populate the custom commands database with some prechosen commands use -addbasiccommands ONCE.
