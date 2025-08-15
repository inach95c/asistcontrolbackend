# Usa una imagen base con Java 17
FROM eclipse-temurin:17-jdk

# Crea el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia todos los archivos del proyecto al contenedor
COPY . .

# Compila el proyecto usando Maven Wrapper
RUN ./mvnw clean install

# Expone el puerto 8080 (ajústalo si usas otro)
EXPOSE 8080

# Ejecuta el .jar generado
CMD ["java", "-jar", "target/asiscontrol.jar"]
