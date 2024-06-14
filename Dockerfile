FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia arquivos Gradle
COPY gradlew .
COPY gradle gradle

# Copia o código-fonte
COPY src src

# Copia arquivos de configuração
COPY build.gradle .
COPY settings.gradle .

# Permite executar o gradlew
RUN chmod +x gradlew

# Compila a aplicação, excluindo os testes
RUN ./gradlew build -x test

# Exponha a porta da aplicação
EXPOSE 8083

# Define a variável de ambiente para a URL do banco de dados
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db-postgres:5432/db_todo

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "build/libs/apidemo-0.0.1-SNAPSHOT.jar"]
