FROM eclipse-temurin:21
MAINTAINER Nelezin Oleg
WORKDIR /app
COPY target/Farm-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","Farm-0.0.1-SNAPSHOT.jar"]
