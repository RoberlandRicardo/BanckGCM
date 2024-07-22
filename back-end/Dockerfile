# # Use uma versão válida do Maven
# FROM maven:3.8.6

# # Porta padrão da aplicação
# EXPOSE 8080

# # Crie o diretório de trabalho
# RUN mkdir -p /usr/src/app

# # Defina o diretório de trabalho
# WORKDIR /usr/src/app

# # Copie o conteúdo do projeto para o diretório de trabalho
# COPY . /usr/src/app

# # Instale as dependências e compile o projeto
# # RUN ./back-end/mvnw -f back-end/pom.xml clean install -DskipTests

# # Comando padrão para rodar o container (substitua pelo comando apropriado para a sua aplicação)
# CMD ["mvn", "spring-boot:run"]

FROM openjdk:17

EXPOSE 8080

ARG JAR_FILE=./back-end/target/demo-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} main.jar

ENTRYPOINT ["java","-jar","/main.jar"]