FROM maven:3.6.1-jdk-8
MAINTAINER Victor N. Skurikhin

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
ADD ./rest-departments-service/	$PROJECT_DIR/rest-departments-service
ADD ./rest-employees-service/   $PROJECT_DIR/rest-employees-service
ADD ./rest-locations-service/   $PROJECT_DIR/rest-locations-service
ADD ./web-service/              $PROJECT_DIR/web-service

RUN mvn -B clean package

CMD [ "sh" ]
