@echo off
docker build -t guides-service:prod .
docker run --rm -p 8082:8082 -v guides-data:/app/data guides-service:prod
