# Указываем базовый образ с поддержкой JDK
FROM openjdk:17-alpine

# Папка внутри контейнера для приложения
WORKDIR /app

# Копируем готовый jar в контейнер
COPY build/libs/service.jar /app/application.jar

# Указываем команду запуска приложения
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
