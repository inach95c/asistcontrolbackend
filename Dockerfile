# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Configurar el directorio de trabajo
WORKDIR /app

# Copiar el archivo .jar generado en la carpeta target
COPY target/soltxd.jar /app/soltxd.jar

# Exponer el puerto en el que corre Spring Boot
EXPOSE 8080

# Definir el comando de inicio
CMD ["java", "-jar", "soltxd.jar"]


