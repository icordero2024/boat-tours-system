@echo off
docker run --rm -it ^
  -p 5173:5173 ^
  -e VITE_TOURS_API_URL=http://localhost:8081 ^
  -e VITE_GUIDES_API_URL=http://localhost:8082 ^
  -v "%cd%":/app ^
  -w /app ^
  node:20-alpine ^
  sh -c "npm install && npm run dev -- --host 0.0.0.0 --port 5173"
