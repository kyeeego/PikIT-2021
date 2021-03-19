#
# BUILD
#
FROM maven:3.6.3-jdk-11-slim as build

COPY pom.xml /home/app/

WORKDIR /home/app/

RUN mvn -B dependency:resolve-plugins dependency:resolve

COPY . .
RUN mvn package -DskipTests

#
# RUN
#
FROM openjdk:11-jre-slim

COPY --from=build home/app/target/pikit-1.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]