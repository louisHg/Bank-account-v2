FROM maven:3.8.4-openjdk-17-slim
LABEL org.opencontainers.image.authors="louis"

# Set the working directory in the container
WORKDIR /app

# Copy the entire project (source code and resources)
COPY . /app/

WORKDIR /app/bank-account-project
RUN mvn clean install -DskipTests

# Package the application
RUN mvn package -DskipTests

# Set the working directory back to /app
WORKDIR /app

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "/app/bank-account-project/target/bank-account-project-0.0.1-SNAPSHOT.jar"]
