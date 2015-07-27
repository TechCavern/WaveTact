WaveTact [![Build Status](https://travis-ci.org/TechCavern/WaveTact.svg?branch=master)](https://travis-ci.org/TechCavern/WaveTact) [![Packagist](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/TechCavern/WaveTact/blob/master/license.md) [![Join the chat at https://gitter.im/TechCavern/WaveTact](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/TechCavern/WaveTact?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
--------

An IRC bot that is based upon PircBotX and sits in #techcavern on the following networks:
- ElectroCode (irc.electrocode.net)
- EsperNET (irc.esper.net)
- SynIRC (irc.synirc.net)
- Freenode (irc.freenode.net)

Building
========
- Run `./gradlew jar`
- Libraries will be downloaded and packed into the JAR
- You will find the JAR in PROJECT_ROOT/build/libs/

Permissions
===========
- By default, everyone is given their permissions based on channel status. Voiced is representative of trusted
- Authentication can be done with NickServ, WaveTact's own authentication system or Hostmask. NickServ authentication has been tested with Anope and Atheme. Others can work, but its not guranteed.

PermLevels
==========
- 20 - Network Administrator (Access to all commands on a specific network)
- 18 - Channel Administrator (Access to all commands below Network Administrator on a specific channel and must be assigned by the controller per channel)
- 15 - Senior Channel Operator (Access to all commands below Founder on a specific channel and must be assigned by the controller per channel)
- 13 - Protected Channel Operator (Access to all commands below Owner on a specific channel)
- 10 - Channel Operator (Access to all commands below Admin on a specific channel)
- 7 - Channel Half-Operator (Access to all commands below Operator on a specific channel)
- 5 - Channel Trusted (Access to all commands below Half-Operator on a specific channel)
- 1 - Registered Access. Everyone has it (Access to all commands below Trusted on a specific channel).
- 0 - Default Access. Everyone has it (Access to all commands below Registered on a specific channel).
- -1 - Ignored (Access to no commands)
- -2 - Ignored by everything except the relay
- -3 - Ignored by everything
- -4 - Banned

Channel-User Properties
=======================
- permlevel - you can set a custom permlevel for a user (Must be integer)
- relaybotsplit - you may assign a bot as a relaybot, and set the value as the character before the actual message is handled
- autoop - determines whether or not the user is gets automatically opped/voiced/ownered/protected depending on their permlevel

Channel Properties
==================
- autounban - this will detect bans and auto-unban a ban after the specified time. (s(econds)/m(inutes)/h(ours)/d(ays)/w(eeks)
- autourl - this will detect and print the title of urls found in channel messages. (True/False)
- topicseparator - this will determine the separator between topic parts
- verboseerrors - send errors to channel instead of notice

Global Config
=================
- wolframalphaapikey - sets the Wolfram Alpha api key
- googleapikey - sets the Google api key (Must be set for shortened Urls to be displayed instead of the long ones)
- wordnikapikey - sets the Wordnik api key
- wundergroundapikey - sets the Wunderground api key
- yandexapikey - sets the Yandex api key
- lastfmapikey - sets the Last.fm api key

Network Properties
=================
- relaychan - Determines the channel from the network to relay
- commandchar - Determines network command char
- pmlog - Logs PMs to a channel
- ignoredhosts - list of ignored hostmasks

Network User Properties
=================
- permlevel - you can set a custom permlevel for a user (Must be integer)

Developing
==========
- You will need to run `gradle idea` each time a db migration file is added as intellij internal gradle support does not handle it properly.

Running
=======
- Make sure you have the WaveTact jar in your current directory.
- Run `java -jar WaveTact.jar`
- Any urls provided by the bot will not be shortened until you have set google's apikey

You can connect to the WaveTact console by running this:
````
java -jar WaveTact.jar --client
````
