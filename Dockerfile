FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /app/target/todo-service-*.jar /myapp.jar
EXPOSE 8080
CMD ["java", "-jar", "/myapp.jar"]