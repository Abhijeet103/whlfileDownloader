# Use Ubuntu as the base image
FROM ubuntu:latest

# Install JDK (OpenJDK 11) and Python
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk python3

# Set environment variables for Java and Python
ENV JAVA_HOME /usr/lib/jvm/java-21-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Create a directory for the Spring Boot application
WORKDIR /app

# Copy the JAR file into the container
COPY  target/whl-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot application will listen on
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]