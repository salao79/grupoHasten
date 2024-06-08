# Use the official OpenJDK 17 image from the Docker Hub
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the project's pom.xml and src directory to the container
COPY pom.xml .
COPY src ./src

# Copy the Maven wrapper to the container if present
COPY .mvn .mvn
COPY mvnw .

# Set the execute permission for the Maven wrapper
RUN chmod +x ./mvnw

# Copy application.properties to the container
COPY application.properties ./src/main/resources/application.properties

# Package the application using Maven
RUN ./mvnw clean package -DskipTests

# Expose port 8080
EXPOSE 8080

# Set the environment variable for JWT secret
ENV JWT_SECRET=${JWT_SECRET}

# Run the application
CMD ["java", "-jar", "target/pruebaSalahdin-0.0.1-SNAPSHOT.jar"]
