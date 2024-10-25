# Usar una imagen base de OpenJDK 21
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR generado en el contenedor
COPY target/gestion-usuarios-0.0.1-SNAPSHOT.jar gestion-usuarios.jar

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Definir el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "gestion-usuarios.jar"]