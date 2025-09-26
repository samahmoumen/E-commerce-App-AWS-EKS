FROM eclipse-temurin:17-jdk

COPY target/*.jar app.jar
COPY . .

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]