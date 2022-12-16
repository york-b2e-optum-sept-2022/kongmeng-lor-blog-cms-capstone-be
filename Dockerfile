FROM gradle:latest as capstone
COPY ./ /build
WORKDIR /build
RUN gradle bootJar

FROM eclipse-temurin:11-jdk-alpine
COPY --from=capstone ./build/build/libs/kongmeng-lor-blog-cms-capstone-*-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
