ALTER TABLE SERVERS RENAME TO OLDSERVERS;
CREATE TABLE SERVERS(
name Text,
port int,
server Text,
nick Text,
channels Text,
bindhost Text,
networkadminaccess boolean,
authtype Text,
networkadmins Text,
nickservcommand Text,
serverpass Text,
nickservnick Text
);

INSERT INTO SERVERS (name, port, server, nick, channels, bindhost, networkadminaccess, authtype,networkadmins) SELECT name, port, server, nick, channels, bindhost, networkadminaccess, authtype,networkadmins FROM OLDSERVERS;

DROP TABLE OLDSERVERS;