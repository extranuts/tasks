FROM bellsoft/liberica-openjdk-alpine:21-37

MAINTAINER ololo

WORKDIR /opt

COPY build/libs/*.jar app.jar

EXPOSE 8081

ENV JAVA_TOOL_OPTIONS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

CMD ["java","-jar","app.jar"]