#!/bin/bash

cd ./IEA-Server/
mvn clean install
cd ../

docker-compose build --parallel
docker-compose up
