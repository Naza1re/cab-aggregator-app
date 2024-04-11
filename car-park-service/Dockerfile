FROM eclipse-temurin:21-jre as builder
WORKDIR workspace
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} car-park-service.jar
RUN java -Djarmode=layertools -jar car-park-service.jar extract

FROM eclipse-temurin:21-jre
WORKDIR workspace
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]