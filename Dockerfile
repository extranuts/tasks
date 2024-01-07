FROM openjdk:19-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar

WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
    
EXPOSE 8081
ENTRYPOINT ["java","-jar","/application.jar"]