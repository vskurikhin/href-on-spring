#!/usr/bin/env bash

ps -ef | grep [j]ava | while read PROC
do
    if [[ $PROC =~ web-service.jar ]]
    then
        set $PROC
        ps -p $2 -O pid,command
        kill $2
    fi
    if [[ $PROC =~ rest-departments-service.jar ]]
    then
        set $PROC
        ps -p $2 -O pid,command
        kill $2
    fi
    if [[ $PROC =~ rest-employees-service.jar ]]
    then
        set $PROC
        ps -p $2 -O pid,command
        kill $2
    fi
    if [[ $PROC =~ rest-locations-service.jar ]]
    then
        set $PROC
        ps -p $2 -O pid,command
        kill $2
    fi
done
