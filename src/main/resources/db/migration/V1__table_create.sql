CREATE TABLE ACCOUNTS (
username Text,
password Text,
randomstring Text
);
CREATE TABLE BANS (
hostmask Text,
network Text,
channel Text,
init BigInt,
time BigInt,
isMute Boolean,
property Text
);

CREATE TABLE CONFIG(
property Text,
value Text
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

CREATE TABLE CHANNELUSERPROPERTY(
network Text,
channel Text,
user Text,
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
authtype Text,
networkadmins Text
);

