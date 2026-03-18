# Guía Docker - Gestión de Personas

## 📋 Requisitos

- Docker instalado ([descargar](https://www.docker.com/products/docker-desktop))
- Docker Compose (viene con Docker Desktop)
- Git (opcional, para clonar el proyecto)

## 🚀 Pasos para Dockerizar

### 1️⃣ Opción 1: Usar Docker Compose (Recomendado - Más Simple)

**Ventajas:**
- Una sola comando para compilar y ejecutar
- Maneja automáticamente la compilación del JAR
- Incluye health check
- Fácil de detener y reiniciar

**En tu terminal:**

```bash
# Navegar al directorio del proyecto
cd c:\Users\usuario\visual-studio-workspaces\demo_matematicas\demo

# Construir y ejecutar la aplicación
docker-compose up --build

# Para ejecutar en segundo plano:
docker-compose up -d --build

# Para ver los logs en tiempo real:
docker-compose logs -f

# Para detener la aplicación:
docker-compose down

# Para detener y eliminar volúmenes:
docker-compose down -v
```

### 2️⃣ Opción 2: Construir Imagen Manualmente (Si prefieres control total)

```bash
# Construir la imagen
docker build -t personas-app:1.0 .

# Ejecutar el contenedor
docker run -p 8080:8080 \
  --name personas-container \
  personas-app:1.0

# Para ejecutar en segundo plano:
docker run -d -p 8080:8080 \
  --name personas-container \
  personas-app:1.0

# Ver logs:
docker logs personas-container

# Ver logs en tiempo real:
docker logs -f personas-container

# Detener el contenedor:
docker stop personas-container

# Eliminar el contenedor:
docker rm personas-container

# Eliminar la imagen:
docker rmi personas-app:1.0
```

### 3️⃣ Opción 3: Compartir la Imagen (Docker Hub)

```bash
# Loguéate en Docker Hub
docker login

# Taguea la imagen con tu usuario
docker tag personas-app:1.0 tu-usuario/personas-app:1.0

# Sube la imagen
docker push tu-usuario/personas-app:1.0

# En otro servidor, descarga y ejecuta:
docker run -d -p 8080:8080 tu-usuario/personas-app:1.0
```

## 📊 Verificar que la Aplicación está Funcionando

```bash
# Abrir en navegador:
http://localhost:8080/personas

# O usar curl para verificar:
curl http://localhost:8080/personas
curl http://localhost:8080/api/personas/export/excel
```

## 📝 Estructura del Dockerfile

```dockerfile
# Etapa 1: Compilación
- Usa Gradle 8.6 con JDK 21
- Compila el proyecto
- Genera el JAR

# Etapa 2: Ejecución (Multi-stage build)
- Usa Eclipse Temurin 21 JRE Alpine (imagen pequeña)
- Copia solo el JAR compilado
- Ejecuta como usuario no-root
- Expone puerto 8080
```

## 🔒 Seguridad

- ✅ Usa usuario no-root (`appuser`)
- ✅ Imagen base minimal (Alpine) = menos vulnerabilidades
- ✅ Multi-stage build = imagen final pequeña (~200MB)
- ✅ Health check incluido

## 📦 Tamaño de la Imagen

- Build stage: ~500MB
- Runtime image: ~200-250MB

## 🌍 Desplegar en Servidor

### Para AWS EC2, DigitalOcean, Linode, etc:

```bash
# 1. SSH al servidor
ssh usuario@servidor.com

# 2. Instalar Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# 3. Clonar el repo o copiar Dockerfile
git clone https://github.com/tu-usuario/tu-repo.git
cd tu-repo

# 4. Ejecutar con Docker Compose
sudo docker-compose up -d

# 5. (Opcional) Usar Nginx como reverse proxy
# Ver: https://nginx.org/en/docs/http/ngx_http_proxy_module.html
```

### Con Nginx Reverse Proxy (Puerto 80/443):

```nginx
upstream app {
    server app:8080;
}

server {
    listen 80;
    server_name tu-dominio.com;

    location / {
        proxy_pass http://app;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 🛑 Troubleshooting

### Puerto 8080 en uso:
```bash
# Cambiar puerto en docker-compose.yml
ports:
  - "8081:8080"  # <-- cambiar aquí
```

### Construir con logs detallados:
```bash
docker-compose up --build --verbose
```

### Limpiar todo y empezar de cero:
```bash
docker-compose down -v
docker system prune -a --volumes
docker-compose up --build
```

### Ver imagenes disponibles:
```bash
docker images
```

### Ver contenedores en ejecución:
```bash
docker ps
docker ps -a  # incluyendo los parados
```

## ✅ Comando Rápido para Empezar

```bash
cd c:\Users\usuario\visual-studio-workspaces\demo_matematicas\demo
docker-compose up --build
```

Luego abre: **http://localhost:8080/personas**

¡Listo! 🎉
