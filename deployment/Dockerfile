FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp

RUN adduser --system appuser

COPY applications/app-service/build/libs/Franquicias.jar Franquicias.jar
RUN sh -c 'touch /Franquicias.jar'

ENV TZ=America/Bogota
ENV JAVA_OPTS=" -XX:+UseContainerSupport -XX:MaxRAMPercentage=70 -Djava.security.egd=file:/dev/./urandom"

USER appuser
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS  -jar Franquicias.jar" ]