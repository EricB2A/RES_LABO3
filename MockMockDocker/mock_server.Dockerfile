# Inspiration : https://github.com/robinong79/docker-mockmock/blob/master/dockerfile

# Image de base
FROM openjdk:8

ENV MOCKMOCK_SMPT_PORT=2500
ENV MOCKMOCK_HTTP_PORT=8080

ADD ["https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true", \
    "/mockmock/"]

WORKDIR /mockmock/

ENTRYPOINT java -jar MockMock.jar -p $MOCKMOCK_SMPT_PORT -h $MOCKMOCK_HTTP_PORT