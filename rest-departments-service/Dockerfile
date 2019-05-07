FROM vstalker/href-on-spring:latest

FROM openjdk:8-jdk
MAINTAINER Victor Skurikhin

ENV TARGET_JAR=rest-locations-service.jar
ENV PROJECT_DIR=/opt/project
ENV TARGET_DIR=$PROJECT_DIR/rest-locations-service/target
WORKDIR $TARGET_DIR

COPY --from=0 $TARGET_DIR/$TARGET_JAR $PROJECT_DIR/

EXPOSE 8080

CMD [ "java", "-jar", "/opt/project/rest-locations-service.jar" ]
