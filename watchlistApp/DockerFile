# Stage 1: Build the application
FROM maven:3-eclipse-temurin-17 AS build
WORKDIR /app  # Set the working directory
COPY pom.xml .  # Copy the pom.xml first to leverage caching
COPY src ./src  # Copy the source code directory
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-alpine
WORKDIR /app  # Ensure a working directory is set
COPY --from=build /app/target/*.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
