#!/usr/bin/env bash
# Fetches Jars

fetch_jar() {
  wget ${1} -O ${2}
}

[ -d libs ] && rm -rf libs
mkdir libs

fetch_jar http://central.maven.org/maven2/org/pircbotx/pircbotx/2.0.1/pircbotx-2.0.1.jar libs/pircbotx.jar
fetch_jar http://central.maven.org/maven2/com/google/guava/guava/17.0/guava-17.0.jar libs/guava.jar
fetch_jar http://central.maven.org/maven2/com/google/code/gson/gson/2.2.4/gson-2.2.4.jar libs/gson.jar
fetch_jar http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.7/slf4j-api-1.7.7.jar libs/slf4j-api.jar
fetch_jar http://central.maven.org/maven2/org/slf4j/slf4j-simple/1.7.7/slf4j-simple-1.7.7.jar libs/slf4j-simple.jar
fetch_jar http://central.maven.org/maven2/org/apache/commons/commons-lang3/3.3.2/commons-lang3-3.3.2.jar libs/commons-lang3.jar
