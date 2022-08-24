FROM openjdk:8u342-jre-slim
COPY ./build/libs/*.jar /miyu-kms.jar
CMD ["java","-jar","miyu-kms.jar","--spring.profiles.active=prod"]