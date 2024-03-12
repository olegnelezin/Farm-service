FROM eclipse-temurin:21
RUN mkdir /opt/app
COPY out/artifacts/Farm_jar/Farm.jar /opt/app/japp.jar
CMD ["java", "-jar", "/opt/app/japp.jar"]