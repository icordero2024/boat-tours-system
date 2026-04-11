#!/usr/bin/env sh
set -e

docker run --rm -it \
  -p 8081:8081 \
  -e SERVER_PORT=8081 \
  -v "$(pwd)":/workspace \
  -v tours-service-m2:/root/.m2 \
  -w /workspace \
  maven:3.9-eclipse-temurin-21 \
  mvn compile exec:java -Dexec.mainClass=com.community.tours.Application
