FROM azul/zulu-openjdk-alpine:20.0.2 as builder

WORKDIR /builder_dir
COPY ./build/libs/reactive-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM azul/zulu-openjdk-alpine:20.0.2
VOLUME /tmp

ARG BUILDER_DIR=builder_dir
ARG USER=service

COPY --from=builder /${BUILDER_DIR}/dependencies/ /home/${USER}/
COPY --from=builder /${BUILDER_DIR}/snapshot-dependencies/ /home/${USER}/
COPY --from=builder /${BUILDER_DIR}/spring-boot-loader/ /home/${USER}/
COPY --from=builder /${BUILDER_DIR}/application/ /home/${USER}/

RUN addgroup -S ${USER} && adduser -S ${USER} -G ${USER}\
 && chown -R ${USER}:0 /home/ && chmod -R g+rwX /home/

WORKDIR /home/$USER
USER ${USER}
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]