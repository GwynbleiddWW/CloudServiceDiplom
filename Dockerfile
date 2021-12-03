FROM openjdk:17-jdk-alpine

EXPOSE 9090

ADD target/CloudServiceDiplom-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]