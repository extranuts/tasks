FROM bellsoft/liberica-openjdk-alpine:21-37

WORKDIR /opt

COPY build/libs/*.jar app.jar

ENV JAVA_TOOL_OPTIONS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD ["java","-jar","app.jar"]