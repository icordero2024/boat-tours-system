#!/usr/bin/env sh
set -e

docker run --rm -it \
  -p 8082:8082 \
  -e SERVER_PORT=8082 \
  -v "$(pwd)":/workspace \
  -v guides-service-m2:/root/.m2 \
  -w /workspace \
  maven:3.9-eclipse-temurin-21 \
  mvn compile exec:java -Dexec.mainClass=com.community.guides.Application
