CREATE TABLE ACCOUNTS (
username Text,
password Text
);
CREATE TABLE BANS (
hostmask Text,
network Text,
channel Text,
init Long,
time Long,
isMute Boolean,
property Text
);

CREATE TABLE CONFIG(
property Text,
value Text
);

CREATE TABLE PERMUSERCHANNELS(
network Text,
channel Text,
account Text,
permlevel int
);

CREATE TABLE CUSTOMCOMMANDS(
permlevel int,
channel Text,
command Text,
value Text,
network Text,
isLocked Boolean,
isAction Boolean
);

CREATE TABLE BLACKLISTS(
type Text,
url Text
);

CREATE TABLE CHANNELPROPERTY(
network Text,
channel Text,
property Text,
value Text
);

CREATE TABLE SERVERS(
name Text,
port int,
server Text,
nick Text,
channels Text,
nickserv Text,
bindhost Text,
networkadminaccess boolean,
authtype Text
);

