# Step 1: Build React app
FROM node:20 AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy full project
COPY . .

# Disable CI mode
ENV CI=false

# Run react-scripts directly
RUN node node_modules/react-scripts/bin/react-scripts.js build

# Step 2: Serve using Nginx
FROM nginx:alpine

COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]