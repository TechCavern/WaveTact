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
- By default, everyone is given their permissions based on channel status. Voiced is representative of trusted
- By default, authors are given trusted status
- Permissions work in conjunction with NickServ. It has been tested with Anope & Atheme. Other services may work, but is not guranteed.

PermLevels
==========
- 9001 - Bot Controller (Access to all commands everywhere)
- 20 - Network Oper (Access to all commands below controller on a specific network)
- 15 - Channel Owner (Access to all commands below controller on a specific channel)
- 10 - Channel Operator (Access to all commands below Owner on a specific channel)
- 7 - Channel Half-Operator (Access to all commands below Operator on a specific channel)
- 5 - Channel Trusted (Access to all commands below Half-Operator on a specific channel)
- 0 - Default Access. Everyone has it.

Running
=======
- Run `java -jar WaveTact.jar`
- WaveTact help is access by the -h or -help parameter
- Adding and removing controllers is available via the -c or -controller parameter.
- Make configuration by using the -addserver or -a parameter
- If you wish to populate the custom commands database with some prechosen commands use -addbasiccommands ONCE.
-
