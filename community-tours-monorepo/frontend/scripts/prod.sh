#!/usr/bin/env sh
set -e

docker build -t community-tours-frontend:prod .
docker run --rm -p 4173:4173 community-tours-frontend:prod
