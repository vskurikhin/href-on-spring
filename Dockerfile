FROM href-maven:latest
MAINTAINER Victor N. Skurikhin

ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
ADD ./rest-departments-service/	$PROJECT_DIR/rest-departments-service
ADD ./rest-employees-service/   $PROJECT_DIR/rest-employees-service
ADD ./rest-locations-service/   $PROJECT_DIR/rest-locations-service
ADD ./web-service/              $PROJECT_DIR/web-service

RUN /bin/sed -i \
 's/^\( \+\)host: "localhost"$/\1host: "pgsql-1-node-1"/g' \
 rest-departments-service/src/main/resources/application.yaml
RUN /bin/sed -i \
 's/^\( \+\)host: "localhost"$/\1host: "pgsql-1-node-1"/g' \
 rest-employees-service/src/main/resources/application.yaml
RUN /bin/sed -i \
 's/^\( \+\)host: "localhost"$/\1host: "pgsql-1-node-1"/g' \
 rest-locations-service/src/main/resources/application.yaml
RUN mvn -B install

CMD [ "sh" ]
