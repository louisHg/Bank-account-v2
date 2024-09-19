# Dockerfile
FROM node:latest

WORKDIR /app

# Install Quasar CLI globally
RUN npm install -g @quasar/cli

# Copy package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the application code
COPY . .

# Expose the port the app runs on
EXPOSE 8080

# Default command to run your application
CMD ["quasar", "dev"]
