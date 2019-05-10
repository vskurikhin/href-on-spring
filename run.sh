#!/bin/sh
# Start all microservices.

export LANG=ru_RU.UTF-8

CS="encoding=UTF-8"  
CP="-cp ./target/classes:./target/dependency/*:./target/*"
JAVA_OPTS="$CP -Dfile.$CS -Dsun.stdout.$CS -Dsun.$CS"

java ${JAVA_OPTS} -jar ./rest-departments-service/target/rest-departments-service.jar &
java ${JAVA_OPTS} -jar ./rest-employees-service/target/rest-employees-service.jar &
java ${JAVA_OPTS} -jar ./rest-locations-service/target/rest-locations-service.jar &

sleep 5

java ${JAVA_OPTS} -jar ./web-service/target/web-service.jar
