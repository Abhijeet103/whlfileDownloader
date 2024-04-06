# Use Ubuntu as the base image
FROM ubuntu:latest

# Install JDK (OpenJDK 11), Python, and pip
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk python3 python3-pip && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# The ENV JAVA_HOME might need adjustment if the OpenJDK version in the repository changes
ENV JAVA_HOME /usr/lib/jvm/java-21-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH

# Copy the built artifact and any other necessary files into the container
# Ensure your build artifact's path is correct
COPY ./target/whl-0.0.1-SNAPSHOT.jar /whl-0.0.1-SNAPSHOT.jar

# Expose the port that the Spring Boot application will listen on
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/whl-0.0.1-SNAPSHOT.jar"]
