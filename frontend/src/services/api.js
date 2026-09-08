import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080/api'
})

export function cadastrarUsuario(dados) {
    return api.post('/usuarios', dados)
}

export function login(email) {
    return api.post('/usuarios/login', {email})
}

export function listarPlaylistsDoUsuario(idUsuario) {
    return api.get(`/usuarios/${idUsuario}/playlists`)
}

export function listarMusicas(idUsuario, filtros = {}) {
    return api.get('/musicas', { params: { idUsuario, ...filtros } })
}

export function cadastrarMusica(dados) {
    return api.post(`/musicas`, dados)
}

export function listarGeneros() {
  return api.get('/generos')
}

export function listarArtistas() {
  return api.get('/artistas')
}

export function listarAlbuns(idArtista) {
  return api.get('/albuns', { params: { idArtista } })
}

export function obterPlaylist(id, idUsuario) {
  return api.get(`/playlists/${id}`, { params: { idUsuario } })
}

export function cadastrarPlaylist(dados) {
  return api.post('/playlists', dados)
}

export function adicionarMusicaNaPlaylist(idPlaylist, dados) {
  return api.post(`/playlists/${idPlaylist}/musicas`, dados)
}

export function removerMusicaDaPlaylist(idPlaylist, idMusica, idUsuario) {
  return api.delete(`/playlists/${idPlaylist}/musicas/${idMusica}`, { params: { idUsuario } })
}