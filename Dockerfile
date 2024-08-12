FROM openjdk:21-jdk

# Define the JAR file location as a build argument
ARG JAR_FILE=target/SpringSecurity-0.0.1-SNAPSHOT.jar

# Copy the JAR file into the container
COPY ${JAR_FILE} app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]
