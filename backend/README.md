# Sonar — Back-end

API REST do projeto Sonar, desenvolvida com Java e Spring Boot.

## Tecnologias

* Java 21
* Spring Boot
* Spring Web
* Spring JDBC
* JdbcTemplate
* PostgreSQL

## Requisitos

* Java 21
* Maven
* Docker e Docker Compose

## Variáveis de ambiente

O Docker Compose depende de variáveis de ambiente para configurar o PostgreSQL. Copie o arquivo de exemplo antes de subir os containers:

```bash
cp .env.exemplo .env
```

## Executando

Na raiz do projeto:
```bash
docker compose up -d
```

Na raiz do diretório `backend`:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
mvnw.cmd spring-boot:run
```

## Banco de dados

A aplicação utiliza PostgreSQL para persistência dos dados.

O banco é executado via Docker Compose (ver `docker-compose.yml` na raiz do projeto); não é necessário instalar o PostgreSQL localmente.

O script de criação das tabelas está disponível em:

```text
script.sql
```

As configurações de conexão com o banco devem ser definidas no arquivo:

```text
src/main/resources/application.properties
```

## Estrutura

```text
src/
├── main/
│   ├── java/
│   └── resources/
└── test/
```

A organização interna da aplicação segue a separação de responsabilidades entre controllers, services, DAOs, DTOs e demais componentes necessários.

## API

A API disponibiliza os endpoints necessários para o funcionamento do Front-end.

A documentação completa do contrato está disponível em:

[`../docs/contrato-api.md`](../docs/contrato-api.md)

## Validação e respostas HTTP

As requisições recebidas pela API são validadas antes da persistência.

Os endpoints utilizam códigos HTTP de acordo com o resultado da operação, conforme definido no contrato da API.
