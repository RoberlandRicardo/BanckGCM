# Use uma versão válida do Maven
FROM maven:3.8.6

# Porta padrão da aplicação
EXPOSE 8080

# Crie o diretório de trabalho
RUN mkdir -p /usr/src/app

# Defina o diretório de trabalho
WORKDIR /usr/src/app

# Copie o conteúdo do projeto para o diretório de trabalho
COPY . /usr/src/app

# Instale as dependências e compile o projeto
RUN mvn clean install -DskipTests

# Comando padrão para rodar o container (substitua pelo comando apropriado para a sua aplicação)
CMD ["mvn", "spring-boot:run"]