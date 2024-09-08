# Use the official OpenJDK image for Java 17
FROM openjdk:17

# Copy the JAR file from the target directory into the image
COPY target/*.jar app.jar

# Set the command to run the app.jar when the container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]