########
# Dockerfile to build pay-service container image
#
########
FROM openjdk:17-slim

LABEL maintainer="Petrulin Alexander"

COPY target/pay-service-*.jar app.jar

EXPOSE 8040

ENTRYPOINT ["java","-jar","/app.jar"]
