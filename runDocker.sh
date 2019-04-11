#!/bin/bash
CURRENT_UID=$(id -u):$(id -g)
echo $(id -u):$(id -g)


docker-compose up
