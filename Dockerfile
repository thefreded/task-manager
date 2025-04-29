## Stage 1 : build with maven builder image
FROM eclipse-temurin:21-jdk AS build

WORKDIR /code
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw && ./mvnw -B org.apache.maven.plugins:maven-dependency-plugin:3.1.2:go-offline
COPY src src
RUN ./mvnw package -DskipTests

## Stage 2 : create the final image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /deployments

# We make four distinct layers so if there are application changes the library layers can be reused
COPY --from=build /code/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /code/target/quarkus-app/*.jar /deployments/
COPY --from=build /code/target/quarkus-app/app/ /deployments/app/
COPY --from=build /code/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080

ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

CMD ["java", "-jar", "quarkus-run.jar"]