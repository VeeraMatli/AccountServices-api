FROM openjdk:8
ADD build/libs/AccountServices-api-0.0.1-SNAPSHOT.jar AccountServices-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","AccountServices-api-0.0.1-SNAPSHOT.jar"]