# Contrato da API — Sonar

Base URL (desenvolvimento): `http://localhost:8080/api`

## Convenções gerais

* `200 OK` — consulta realizada com sucesso
* `201 Created` — criação realizada com sucesso (retorna o recurso criado + cabeçalho)
* `204 No Content` — operação concluída sem corpo de retorno (ex.: DELETE)
* `400 Bad Request` — dados inválidos
* `404 Not Found` — recurso não encontrado
* `409 Conflict` — o recurso já existe (ex.: e-mail já cadastrado)

Formato padrão de erro:

```json
{
  "status": 400,
  "mensagem": "Título é obrigatório",
  "erros": [
    { "campo": "titulo", "mensagem": "não pode ser vazio" }
  ]
}
```

---

## Usuários

Cadastro e "login" simplificados (sem senha). O Front-end identifica o usuário pelo `idUsuario`, armazenado em `localStorage` após cadastro ou login, e envia esse `idUsuario` em todas as operações de música e playlist (cada usuário só acessa os próprios recursos).

### `POST /api/usuarios`

Cadastra um novo usuário.

**Corpo da requisição:**

```json
{
  "nome": "Marlon",
  "email": "marlon@exemplo.com"
}
```

Validações:

* `nome`: obrigatório, não vazio
* `email`: obrigatório, formato válido

**Resposta `201 Created`:**

```json
{
  "idUsuario": 1,
  "nome": "Marlon",
  "email": "marlon@exemplo.com"
}
```

Cabeçalho `Location: /api/usuarios/{id}`.

**Resposta `400 Bad Request`:** `nome` ou `email` ausentes/inválidos.

**Resposta `409 Conflict`:** e-mail já cadastrado.

```json
{
  "status": 409,
  "mensagem": "E-mail já cadastrado",
  "erros": [
    { "campo": "email", "mensagem": "já está em uso" }
  ]
}
```

### `POST /api/usuarios/login`

Busca um usuário pelo e-mail (usado no fluxo de "login").

**Resposta `200 OK`:**

```json
{
  "idUsuario": 1,
  "nome": "Marlon",
  "email": "marlon@exemplo.com"
}
```

**Resposta `404 Not Found`:** e-mail não cadastrado (Front-end deve sugerir cadastro).

### `GET /api/usuarios/{id}/playlists`

Lista as playlists do usuário.

**Resposta `200 OK`:**

```json
[
  {
    "idPlaylist": 1,
    "nome": "Minha Playlist",
    "totalMusicas": 5,
    "criadoEm": "2026-08-20T14:00:00"
  }
]
```

**Resposta `404 Not Found`:** se o usuário não existir.

---

## Músicas

Todas as operações de música são escopadas por `idUsuario`: cada usuário só enxerga, edita e remove as músicas que ele mesmo cadastrou.

### `GET /api/musicas`

Lista as músicas do usuário. Suporta filtros combináveis por meio de parâmetros de consulta.

| Parâmetro    | Tipo    | Obrigatório | Descrição                                                                           |
| ------------ | ------- | ----------- | ------------------------------------------------------------------------------------ |
| `idUsuario`  | integer | **sim**     | escopo obrigatório — retorna apenas músicas cadastradas por esse usuário             |
| `titulo`     | string  | não         | busca parcial, sem distinção entre maiúsculas e minúsculas                           |
| `idArtista`  | integer | não         | filtra por artista                                                                   |
| `idAlbum`    | integer | não         | filtra por álbum                                                                     |
| `idGenero`   | integer | não         | filtra por gênero                                                                    |
| `ordenarPor` | string  | não         | `dataCadastro` (padrão), `titulo`, `duracao`, `artista`                              |
| `direcao`    | string  | não         | `asc` ou `desc` (padrão: `desc` quando `ordenarPor=dataCadastro`, `asc` nos demais)  |

**Exemplo de requisição:**

```text
GET /api/musicas?idUsuario=1&idGenero=2&ordenarPor=titulo&direcao=asc
```

**Resposta `200 OK`:**

```json
[
  {
    "idMusica": 1,
    "titulo": "Nome da Música",
    "duracao": 215,
    "caminhoAudio": "https://cdn.exemplo.com/audio/musica1.mp3",
    "criadoEm": "2026-08-20T14:32:00",
    "album": {
      "idAlbum": 3,
      "titulo": "Nome do Álbum",
      "ano": 2020
    },
    "artista": {
      "idArtista": 5,
      "nome": "Nome do Artista"
    },
    "genero": {
      "idGenero": 2,
      "nome": "Rock"
    }
  },
  {
    "idMusica": 2,
    "titulo": "Música sem álbum",
    "duracao": 190,
    "caminhoAudio": "https://cdn.exemplo.com/audio/musica2.mp3",
    "criadoEm": "2026-08-21T09:10:00",
    "album": null,
    "artista": {
      "idArtista": 5,
      "nome": "Nome do Artista"
    },
    "genero": {
      "idGenero": 2,
      "nome": "Rock"
    }
  }
]
```

> `album` pode ser `null` (música sem álbum). `artista` e `genero` nunca são `null` — ambos são obrigatórios.

**Resposta `400 Bad Request`:** se `idUsuario` não for informado.

### `GET /api/musicas/{id}`

Detalhes de uma música.

| Parâmetro   | Tipo    | Obrigatório | Descrição                                     |
| ----------- | ------- | ----------- | ---------------------------------------------- |
| `idUsuario` | integer | **sim**     | valida que a música pertence a esse usuário    |

**Exemplo de requisição:**

```text
GET /api/musicas/1?idUsuario=1
```

**Resposta `200 OK`:** mesmo formato do item de listagem.

**Resposta `404 Not Found`:** se não existir, **ou** se existir mas pertencer a outro `idUsuario`.

### `POST /api/musicas`

Cadastra uma música.

**Corpo da requisição:**

```json
{
  "titulo": "Nome da Música",
  "duracao": 215,
  "caminhoAudio": "https://cdn.exemplo.com/audio/musica1.mp3",
  "idAlbum": 3,
  "idArtista": 5,
  "idGenero": 2,
  "idUsuario": 1
}
```

Validações:

* `titulo`: obrigatório, não vazio
* `duracao`: obrigatório, inteiro positivo (segundos)
* `caminhoAudio`: obrigatório, não vazio
* `idGenero`: obrigatório, deve existir
* `idArtista`: obrigatório, deve existir
* `idAlbum`: opcional — se enviado, deve existir
* `idUsuario`: obrigatório, deve existir

**Regra de consistência (aplicada no Back-end, não no banco):**

* Se `idAlbum` for informado, o Back-end ignora um `idArtista` divergente e preenche `idArtista` automaticamente com o artista do álbum (`album.id_artista`).
* Se `idAlbum` não for informado, `idArtista` é usado como veio no corpo.

**Resposta `201 Created`:** música criada (mesmo formato de `GET /musicas/{id}`), cabeçalho `Location: /api/musicas/{id}`.

**Resposta `400 Bad Request`:** dados inválidos.

### `PUT /api/musicas/{id}`

Atualiza uma música (mesmo corpo do POST, incluindo `idUsuario`).

**Resposta `200 OK`:** música atualizada.

**Resposta `404 Not Found`:** se não existir, ou se pertencer a outro `idUsuario`.

**Resposta `400 Bad Request`:** dados inválidos.

### `DELETE /api/musicas/{id}`

Remove uma música e suas associações em `musica_playlist`.

| Parâmetro   | Tipo    | Obrigatório | Descrição                                     |
| ----------- | ------- | ----------- | ---------------------------------------------- |
| `idUsuario` | integer | **sim**     | valida que a música pertence a esse usuário    |

**Resposta `204 No Content`.**

**Resposta `404 Not Found`:** se não existir, ou se pertencer a outro `idUsuario`.

---

## Gêneros / Artistas / Álbuns

Recursos de apoio a formulários e filtros, compartilhados entre todos os usuários (não são escopados por `idUsuario`).

> Decisão: dados fixos via seed no `script.sql`, sem endpoints de cadastro por enquanto. Endpoints `POST` para artista/gênero podem ser adicionados depois se houver tempo.

### `GET /api/generos`

```json
[
  { "idGenero": 1, "nome": "Rock" },
  { "idGenero": 2, "nome": "Pop" }
]
```

### `GET /api/artistas`

```json
[
  { "idArtista": 1, "nome": "Artista X" }
]
```

### `GET /api/albuns`

Suporta filtro opcional `?idArtista=`.

```json
[
  {
    "idAlbum": 1,
    "titulo": "Álbum Y",
    "ano": 2019,
    "idArtista": 1
  }
]
```

---

## Playlists

Todas as operações de playlist são escopadas por `idUsuario`: cada usuário só enxerga, edita e remove as playlists que ele mesmo criou. A listagem geral acontece via `GET /api/usuarios/{id}/playlists` (ver seção Usuários).

### `GET /api/playlists/{id}`

Retorna a playlist com as músicas ordenadas por `posicao`.

| Parâmetro   | Tipo    | Obrigatório | Descrição                                      |
| ----------- | ------- | ----------- | ------------------------------------------------ |
| `idUsuario` | integer | **sim**     | valida que a playlist pertence a esse usuário    |

**Exemplo de requisição:**

```text
GET /api/playlists/1?idUsuario=1
```

**Resposta `200 OK`:**

```json
{
  "idPlaylist": 1,
  "nome": "Minha Playlist",
  "musicas": [
    {
      "idMusica": 1,
      "titulo": "Música A",
      "posicao": 1,
      "duracao": 200,
      "caminhoAudio": "..."
    },
    {
      "idMusica": 4,
      "titulo": "Música B",
      "posicao": 2,
      "duracao": 180,
      "caminhoAudio": "..."
    }
  ]
}
```

**Resposta `404 Not Found`:** se não existir, ou se pertencer a outro `idUsuario`.

### `POST /api/playlists`

```json
{
  "nome": "Minha Playlist",
  "idUsuario": 1
}
```

Validações:

* `nome`: obrigatório, não vazio
* `idUsuario`: obrigatório, deve existir

**Resposta `201 Created`.**

**Resposta `400 Bad Request`:** dados inválidos ou `idUsuario` inexistente.

### `PUT /api/playlists/{id}`

Renomeia a playlist.

```json
{
  "nome": "Novo Nome",
  "idUsuario": 1
}
```

**Resposta `200 OK`.**

**Resposta `404 Not Found`:** se não existir, ou se pertencer a outro `idUsuario`.

### `DELETE /api/playlists/{id}`

Remove a playlist e suas associações.

| Parâmetro   | Tipo    | Obrigatório | Descrição                                      |
| ----------- | ------- | ----------- | ------------------------------------------------ |
| `idUsuario` | integer | **sim**     | valida que a playlist pertence a esse usuário    |

**Resposta `204 No Content`.**

**Resposta `404 Not Found`:** se não existir, ou se pertencer a outro `idUsuario`.

### `POST /api/playlists/{id}/musicas`

Adiciona uma música à playlist (ao final da fila).

```json
{
  "idMusica": 4,
  "idUsuario": 1
}
```

Validações:

* `idMusica` deve existir **e pertencer ao mesmo `idUsuario`**
* `idUsuario` deve corresponder ao dono da playlist
* rejeitar duplicidade (a mesma música já está na playlist) → `400 Bad Request`

**Resposta `201 Created`:** retorna a entrada criada com `posicao` calculada.

**Resposta `404 Not Found`:** se a playlist ou a música não existir, ou não pertencer a esse `idUsuario`.

### `DELETE /api/playlists/{id}/musicas/{idMusica}`

Remove a música da playlist e reindexa as posições seguintes.

| Parâmetro   | Tipo    | Obrigatório | Descrição                                      |
| ----------- | ------- | ----------- | ------------------------------------------------ |
| `idUsuario` | integer | **sim**     | valida que a playlist pertence a esse usuário    |

**Resposta `204 No Content`.**

**Resposta `404 Not Found`:** se a associação não existir, ou a playlist não pertencer a esse `idUsuario`.

---

## CORS

Origem permitida em desenvolvimento: `http://localhost:5173` (porta padrão do Vite).