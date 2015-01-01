WaveTact [![Build Status](https://travis-ci.org/TechCavern/WaveTact.svg?branch=master)](https://travis-ci.org/TechCavern/WaveTact) [![Packagist](https://img.shields.io/packagist/l/doctrine/orm.svg?style=flat)](https://github.com/TechCavern/WaveTact/blob/master/license.txt)
--------
An IRC bot that is based upon PircBotX and sits in #techcavern on the following networks:
- ElectroCode (irc.electrocode.net)
- EsperNET (irc.esper.net)
- Xertion (irc.xertion.org)
- Overdrive (irc.overdrive.pw)
- Freenode (irc.freenode.net)
- ObsidianIRC (irc.obsidianirc.net)

Building
========
- Run `./gradlew jar`
- Libraries will be downloaded and packed into the JAR
- You will find the JAR in PROJECT_ROOT/build/libs/

Permissions
===========
- By default, everyone is given their permissions based on channel status. Voiced is representative of trusted
- Authentication can be done with NickServ, WaveTact's own authentication system or Nick. NickServ authentication has been tested with Anope and Atheme. Others can work, but its not guranteed.

PermLevels
==========
- 20 - Network Administrator (Access to all commands on a specific network)
- 18 - Channel Administrator (Access to all commands below Network Administrator on a specific channel and must be assigned by the controller per channel)
- 15 - Channel Ownered Operator (Access to all commands below Founder on a specific channel and must be assigned by the controller per channel)
- 13 - Channel Protected Operator (Access to all commands below Owner on a specific channel)
- 10 - Channel Operator (Access to all commands below Admin on a specific channel)
- 7 - Channel Half-Operator (Access to all commands below Operator on a specific channel)
- 5 - Channel Trusted (Access to all commands below Half-Operator on a specific channel)
- 0 - Default Access. Everyone has it.
- -1 - Banned

Developing
==========
- You will need to run `gradle idea` each time the build file is modified as intellij internal gradle support does not handle it properly. 

Running
=======
- Make sure you have the WaveTact jar in your current directory.
- Run `java -jar WaveTact.jar`

If you wish to populate the custom commands database with some prechosen commands use the console command ````addbasiccommands```` ONCE.

You can connect to the WaveTact console by running this:
````
java -jar WaveTact.jar console
````

Note that you'll have to add a controller and network at least once.
You will have to restart WaveTact after adding the latter, we haven't yet implemented a proper way to live-add a channel.
