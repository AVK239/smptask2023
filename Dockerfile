FROM openjdk:11-jre-slim

# Путь к JAR-файлу
ARG JAR_FILE=target/my-minio-app.jar

# Копирование JAR-файла в контейнер
COPY ${JAR_FILE} app.jar

# Команда для запуска приложения в контейнере
ENTRYPOINT ["java", "-jar", "/app.jar"]
