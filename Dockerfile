FROM amazoncorretto:17-alpine

WORKDIR /app

COPY . /app

RUN mkdir -p images

RUN chmod +x gradlew

RUN ./gradlew build

EXPOSE 8080

CMD ["java", "-jar", "build/libs/graduation-project-spring-v1.jar"]