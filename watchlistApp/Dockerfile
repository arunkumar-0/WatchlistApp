# Stage 1: Build the application
FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app  # Set the working directory

# Adjust paths for the Dockerfile location
COPY ./pom.xml .  # Copy pom.xml (relative to Dockerfile)
COPY ./src ./src  # Copy the source directory (relative to Dockerfile)

# Build the project and skip tests for faster build
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-alpine
WORKDIR /app  # Ensure a working directory is set

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
