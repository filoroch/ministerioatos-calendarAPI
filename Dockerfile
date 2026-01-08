# Multi-stage Dockerfile for a Maven + Spring Boot application with hot reload support

FROM maven:3.9.4-eclipse-temurin-21 AS builder
WORKDIR /.

# Copy Maven wrapper and pom first to leverage layer caching
COPY pom.xml mvnw ./
COPY .mvn .mvn

# Copy source and build
COPY src ./src
RUN mvn -B -DskipTests package

# Prepare runnable jar
RUN mkdir -p /app && cp target/*.jar /app/app.jar

FROM eclipse-temurin:21-jre
WORKDIR /app

# JVM options can be overridden at runtime with --env JAVA_OPTS
# Added -XX:+TieredCompilation for faster recompilation on hot reload
ARG JAVA_OPTS="-Xms256m -Xmx512m -XX:+TieredCompilation"
ENV JAVA_OPTS=${JAVA_OPTS}

# Spring DevTools configuration for hot reload
ENV SPRING_DEVTOOLS_RESTART_ENABLED=true
ENV SPRING_DEVTOOLS_RESTART_POLL_INTERVAL=1000
ENV SPRING_DEVTOOLS_RESTART_QUIET_PERIOD=400

# Spring Profiles: configurações predefinidas por ambiente
ENV SPRING_PROFILES_ACTIVE=dev

COPY --from=builder /app/app.jar /app/app.jar

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]