#!/usr/bin/env sh
set -e

docker build -t tours-service:prod .
docker run --rm -p 8081:8081 -v tours-data:/app/data tours-service:prod
