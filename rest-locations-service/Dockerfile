FROM docker.io/vskurikhin/href:latest

FROM openjdk:8-jdk
`
MAINTAINER Victor N. Skurikhin
ENV TARGET_JAR=rest-locations-service.jar
ENV PROJECT_DIR=/opt/project
ENV TARGET_DIR=$PROJECT_DIR/rest-locations-service/target

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

COPY --from=0 $TARGET_DIR/$TARGET_JAR $PROJECT_DIR/

EXPOSE 8001

CMD [ "java", "-jar", "/opt/project/rest-locations-service.jar" ]
