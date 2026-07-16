FROM node:20 AS build

WORKDIR /app

COPY package*.json ./

RUN npm install --fetch-timeout=600000 --fetch-retries=5

COPY . .

ENV CI=false

RUN node node_modules/react-scripts/bin/react-scripts.js build

FROM nginx:alpine

COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]