# Usa una imagen base ligera con Java 17
FROM eclipse-temurin:17-jdk-jammy

# Crea un directorio para la app
WORKDIR /app

# Copia el JAR generado
COPY target/books-authors-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto del backend
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
