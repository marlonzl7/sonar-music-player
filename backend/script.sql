CREATE TABLE usuario (
    id_usuario BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE artista (
    id_artista BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE album (
    id_album BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_artista BIGINT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    ano INT,

    CONSTRAINT fk_album_artista
        FOREIGN KEY (id_artista) REFERENCES artista(id_artista)
        ON DELETE RESTRICT
);

CREATE TABLE genero (
    id_genero BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE musica (
    id_musica BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_genero BIGINT NOT NULL,
    id_artista BIGINT NOT NULL,
    id_album BIGINT,
    titulo VARCHAR(150) NOT NULL,
    duracao INT NOT NULL,
    caminho_audio VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_musica_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE,

    CONSTRAINT fk_musica_genero
        FOREIGN KEY (id_genero) REFERENCES genero(id_genero)
        ON DELETE RESTRICT,

    CONSTRAINT fk_musica_artista
        FOREIGN KEY (id_artista) REFERENCES artista(id_artista)
        ON DELETE RESTRICT,

    CONSTRAINT fk_musica_album
        FOREIGN KEY (id_album) REFERENCES album(id_album)
        ON DELETE SET NULL,

    CONSTRAINT chk_duracao
        CHECK (duracao > 0)
);

CREATE TABLE playlist (
    id_playlist BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_playlist_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE,

    CONSTRAINT uq_playlist_usuario_nome
        UNIQUE (id_usuario, nome)
);

CREATE TABLE musica_playlist (
    id_musica BIGINT NOT NULL,
    id_playlist BIGINT NOT NULL,
    posicao INT NOT NULL,

    CONSTRAINT pk_musica_playlist
        PRIMARY KEY (id_musica, id_playlist),

    CONSTRAINT fk_musica_playlist_musica
        FOREIGN KEY (id_musica) REFERENCES musica(id_musica)
        ON DELETE CASCADE,

    CONSTRAINT fk_musica_playlist_playlist
        FOREIGN KEY (id_playlist) REFERENCES playlist(id_playlist)
        ON DELETE CASCADE,

    CONSTRAINT chk_posicao
        CHECK (posicao > 0)
);

CREATE INDEX idx_musica_titulo ON musica (titulo);
CREATE INDEX idx_musica_artista ON musica (id_artista);
CREATE INDEX idx_musica_album ON musica (id_album);
CREATE INDEX idx_musica_genero ON musica (id_genero);
CREATE INDEX idx_musica_usuario ON musica (id_usuario);
CREATE INDEX idx_album_artista ON album (id_artista);
CREATE INDEX idx_playlist_usuario ON playlist (id_usuario);

INSERT INTO genero (nome) VALUES
    ('Rock'),
    ('Pop'),
    ('Hip Hop'),
    ('Eletrônica'),
    ('MPB'),
    ('Jazz'),
    ('Clássica'),
    ('Sertanejo');

INSERT INTO artista (nome) VALUES
    -- Rock
    ('Legião Urbana'),
    ('Titãs'),
    ('Capital Inicial'),
    ('CPM 22'),
    -- Pop
    ('Ivete Sangalo'),
    -- Hip Hop
    ('Racionais MC''s'),
    ('Facção Central'),
    ('Sabotage'),
    ('MV Bill'),
    ('Rappin'' Hood'),
    ('BK'),
    ('Djonga'),
    ('Emicida'),
    -- Eletrônica
    ('Alok'),
    ('Vintage Culture'),
    ('KVSH'),
    -- MPB
    ('Elis Regina'),
    ('Marisa Monte'),
    ('Chico Buarque'),
    ('Gilberto Gil'),
    -- Jazz
    ('Tom Jobim'),
    ('João Gilberto'),
    -- Clássica
    ('Heitor Villa-Lobos'),
    -- Sertanejo
    ('Jorge & Mateus'),
    ('Henrique & Juliano');