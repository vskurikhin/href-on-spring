FROM docker.io/vskurikhin/href:latest

FROM openjdk:8-jdk

MAINTAINER Victor N. Skurikhin
ENV TARGET_JAR=rest-departments-service.jar
ENV PROJECT_DIR=/opt/project
ENV TARGET_DIR=$PROJECT_DIR/rest-departments-service/target
WORKDIR $TARGET_DIR

COPY --from=0 $TARGET_DIR/$TARGET_JAR $PROJECT_DIR/

EXPOSE 8002

CMD [ "java", "-jar", "/opt/project/rest-departments-service.jar" ]
