#!/bin/sh

ps -ef | grep [j]ava | while read PROC
do
    if [[ $PROC =~ web-service-1.0-SNAPSHOT.jar ]]
    then
        set $PROC
        ps -p $2 -O pid,command
        kill $2
    fi
    if [[ $PROC =~ rest-employees-service-1.0-SNAPSHOT.jar ]]
    then
        set $PROC
        ps -p $2 -O pid,command
        kill $2
    fi
    if [[ $PROC =~ rest-locations-service-1.0-SNAPSHOT.jar ]]
    then
        set $PROC
        ps -p $2 -O pid,command
        kill $2
    fi
done
