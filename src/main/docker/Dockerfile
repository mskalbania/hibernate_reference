FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY *.jar app.jar
ENTRYPOINT ["java","-jar","-Dspring.datasource.url=${DB_URL}","-Dspring.datasource.username=${DB_USER}","-Dspring.datasource.password=${DB_PASSWORD}","/app.jar"]