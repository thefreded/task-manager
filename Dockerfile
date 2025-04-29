## Stage 1 : build with maven builder image
FROM registry.access.redhat.com/ubi8/openjdk-21:1.1 AS build

COPY --chown=185 mvnw /code/mvnw
COPY --chown=185 .mvn /code/.mvn
COPY --chown=185 pom.xml /code/
USER 185
WORKDIR /code
RUN ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
COPY src /code/src
RUN ./mvnw package -DskipTests

## Stage 2 : create the final image
FROM registry.access.redhat.com/ubi8/openjdk-21:1.1

# We make four distinct layers so if there are application changes the library layers can be reused
COPY --from=build --chown=185 /code/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /code/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /code/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /code/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"