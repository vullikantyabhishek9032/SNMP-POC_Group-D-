# Step 1: Build React app
FROM node:18 AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies and fix permissions
RUN npm install && chmod +x node_modules/.bin/react-scripts

# Install dependencies
RUN npm install

# Copy full project
COPY . .

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