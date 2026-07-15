# Step 1: Build React app
FROM node:20 AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies and fix permissions
RUN npm install && chmod -R 755 node_modules/.bin

# Copy full project
COPY . .

# Disable CI mode
ENV CI=false

# Build production app
RUN npm run build

# Step 2: Serve using Nginx
FROM nginx:alpine

# Copy build output to nginx
COPY --from=build /app/build /usr/share/nginx/html

# Expose port
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]