FROM maven:3.9-eclipse-temurin-8 AS build
WORKDIR /app
COPY pom.xml .
COPY mvnw .
COPY .mvn ./.mvn
COPY src ./src
COPY config ./config
COPY checkstyle.xml .
RUN chmod +x mvnw \
	&& cp src/main/resources/application.properties.example src/main/resources/application.properties \
	&& ./mvnw -B -DskipTests package

FROM tomcat:9.0-jdk8-temurin
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/SpringHibernateExample.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
