FROM openjdk:17-alpine
ADD target/titanic-query.jar titanic-query.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar",  "titanic-query.jar"]