#!/usr/bin/env bash
-- Kafka Commands

kafka-topics --list --zookeeper zookeeper-1:12181
kafka-topics --describe --topic Product.change --zookeeper zookeeper-1:12181
kafka-topics --delete --topic Product.change --zookeeper zookeeper-1:12181
./kafka-topics.sh --create  --zookeeper zookeeper-1:12181 --replication-factor 3 --partitions 3 --topic Product.change

-- Zookeeper Commands

root@zookeeper-1:/usr/bin# zookeeper-shell 127.0.0.1:12181
ls /
ls /brokers
ls /brokers/topics
ls /consumers


-- Docker Commands

docker exec -it docker_zookeeper-1_1 bash

Kafkacat

kafkacat -P -b kafka-1:19092 -t helloworld_topic
kafkacat -C -b kafka-3:39092 -t helloworld_topic

docker pause <container-id/name>
docker unpause <container-id/name>
