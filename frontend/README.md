# Sonar — Front-end

Aplicação web do projeto Sonar, desenvolvida com React e Vite.

## Tecnologias

* React
* Vite
* JavaScript
* JSX
* CSS Modules

## Requisitos

* Node.js
* npm

## Instalação

Dentro do diretório `frontend`:

```bash
npm install
```

## Executando

Para iniciar o servidor de desenvolvimento:

```bash
npm run dev
```

## Estrutura

```text
src/
├── components/
├── pages/
├── services/
├── App.jsx
└── main.jsx
```

* **`components/`** — componentes reutilizáveis da aplicação.
* **`pages/`** — telas da aplicação.
* **`services/`** — comunicação com a API.
* **`App.jsx`** — componente principal da aplicação.
* **`main.jsx`** — ponto de entrada do Front-end.

## Integração com a API

O Front-end consome a API REST desenvolvida no diretório `backend/`.

As informações exibidas na aplicação são obtidas por meio da API e os dados cadastrados são enviados ao Back-end para persistência.

O contrato de integração está disponível em:

[`../docs/contrato-api.md`](../docs/contrato-api.md)

## Estilização

A aplicação utiliza CSS Modules para a estilização dos componentes.

Os estilos são definidos em arquivos com a extensão:

```text
.module.css
```

## Funcionalidades

As funcionalidades da aplicação serão implementadas de acordo com os requisitos definidos para o Projeto Integrador.

## Build

Para gerar a versão de produção:

```bash
npm run build
```
