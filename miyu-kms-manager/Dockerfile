FROM frolvlad/alpine-java:jdk8-slim
COPY ./build/libs/*.jar /miyu-kms.jar
CMD ["java","-jar","miyu-kms.jar","--spring.profiles.active=prod"]