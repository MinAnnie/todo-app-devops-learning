# Base image for caching dependencies
FROM gradle:8.7-jdk-21-and-22-alpine AS base

# Set working directory
WORKDIR /build

# Copy Gradle configuration files and source code
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src ./src

# Download dependencies (useful for caching)
RUN gradle --no-daemon clean build

# Build stage
FROM base AS build

# Run the build
RUN gradle --no-daemon clean build

# Production stage
# Use JRE 21 to match the compiled class version
FROM eclipse-temurin:21-jre AS production

# Set working directory
WORKDIR /srv

# Copy the jar from the build stage
COPY --from=build /build/build/libs/*.jar ./app.jar

# Expose application port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "./app.jar"]
