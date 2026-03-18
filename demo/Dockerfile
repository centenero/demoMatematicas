# Etapa 1: Compilar la aplicación
FROM gradle:8.6-jdk21 AS builder

WORKDIR /app

# Copiar archivos de configuración de Gradle
COPY build.gradle settings.gradle gradle.properties* ./

# Copiar código fuente
COPY src ./src

# Compilar y construir el JAR
RUN gradle clean build -x test

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear usuario no-root para Security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copiar el JAR desde la etapa de compilación
COPY --from=builder /app/build/libs/*.jar app.jar

# Cambiar permisos
RUN chown -R appuser:appgroup /app

# Usar el usuario no-root
USER appuser

# Exponer el puerto
EXPOSE 8080

# Variable de entorno para la aplicación
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
