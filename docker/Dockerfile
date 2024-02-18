FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ../modules/application/build/libs/application-all.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
#CMD ["tail", "-f", "/dev/null"]