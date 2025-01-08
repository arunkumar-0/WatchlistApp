# Stage 1: Build the application
FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app  # Set the working directory

# Copy the pom.xml and source code
COPY ./pom.xml .  # Copy pom.xml (relative to Dockerfile)
COPY ./src ./src  # Copy the source directory (relative to Dockerfile)

# Build the project and skip tests for faster build
RUN mvn clean package -DskipTests

# Debugging step to check if the JAR is correctly built
RUN ls -l /app/target  # Check the contents of the target folder

# Stage 2: Run the application
FROM eclipse-temurin:17-alpine
WORKDIR /app  # Ensure a working directory is set

# Copy the built JAR from the build stage using the absolute path
COPY --from=build /app/target/*.jar /app/app.jar  # Ensure the JAR is in the expected location

# Expose port 8080 for the application
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
