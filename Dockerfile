FROM eclipse-temurin:21.0.2_13-jdk-jammy as builder

WORKDIR /app

COPY .mvn ./.mvn
COPY ./mvnw ./pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src/ ./src/
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21.0.2_13-jre-jammy AS final

WORKDIR /app

EXPOSE 8080

COPY --from=builder /app/target/*.jar /app

ENTRYPOINT ["java", "-jar", "/app/todo-1.0.jar"]
