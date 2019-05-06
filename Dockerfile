FROM openjdk:8-alpine
VOLUME /tmp
ADD target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT exec java -jar -Dspring.profiles.active=dev /app.jar