# Sonar

Aplicação web desenvolvida como Projeto Integrador das disciplinas de Programação Web Front-end e Back-end da SPTech.

O projeto é composto por uma aplicação Front-end em React e uma API REST desenvolvida com Java e Spring Boot, responsável pela persistência e disponibilização dos dados.

## Tecnologias

### Front-end

* React
* Vite
* JavaScript
* JSX
* CSS Modules

### Back-end

* Java
* Spring Boot
* JdbcTemplate
* PostgreSQL

## Estrutura do projeto

```text
sonar/
├── backend/
├── frontend/
├── docs/
└── README.md
```

* **`backend/`** — API REST e persistência dos dados.
* **`frontend/`** — aplicação web em React.
* **`docs/`** — documentação e contrato de integração entre Front-end e Back-end.

## Pré-requisitos

  * Java 21
  * Maven
  * Node.js
  * npm
  * Docker e Docker Compose

## Executando o projeto

### 1. Back-end

```bash
docker compose up -d
cd backend
./mvnw spring-boot:run
```

A API será executada na porta configurada pela aplicação.

Mais informações estão disponíveis em [`backend/README.md`](backend/README.md).

### 2. Front-end

Em outro terminal:

```bash
cd frontend
npm install
npm run dev
```

Mais informações estão disponíveis em [`frontend/README.md`](frontend/README.md).

## Integração

O Front-end consome os dados disponibilizados pela API REST do Back-end.

```text
Front-end -> Backend -> PostgreSQL
```

O contrato de integração entre as aplicações está documentado em [`docs/contrato-api.md`](docs/contrato-api.md).

## Documentação

* [Back-end](backend/README.md)
* [Front-end](frontend/README.md)
* [Contrato da API](docs/contrato-api.md)

## Projeto Integrador

Projeto desenvolvido para integração entre as disciplinas de Programação Web Front-end e Programação Web Back-end.

## Status

Em desenvolvimento.
