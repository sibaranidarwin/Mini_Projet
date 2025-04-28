# Gunakan base image OpenJDK
FROM openjdk:17-jdk-slim

# Buat folder kerja di dalam container
WORKDIR /app

# Copy file JAR ke container
COPY target/mini_project.jar app.jar

# Jalankan aplikasi
CMD ["java", "-jar", "app.jar"]
