CREATE TABLE ACCOUNTS (
username String,
password String
);
CREATE TABLE BANS (
hostmask String,
network String,
channel String,
init Long,
time Long,
isMute Boolean,
property String
);

CREATE TABLE CONFIG(
property String,
value String
);

CREATE TABLE PERMUSERCHANNELS(
network String,
channel String,
account String,
permlevel int
);

CREATE TABLE CUSTOMCOMMANDS(
network int,
channel String,
permlevel String,
locked String,
isAction Boolean,
);

CREATE TABLE BLACKLISTS(
type String,
url String
);

CREATE TABLE CHANNELPROPERTY(
network String,
channel String,
property String,
value String
);

CREATE TABLE SERVERS(
name String,
port int,
server String,
nick String,
channels String,
nickserv String,
bindhost String,
networkadminaccess boolean,
authtype String
);

