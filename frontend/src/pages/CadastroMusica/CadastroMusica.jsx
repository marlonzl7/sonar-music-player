import { useState } from "react"
import { useEffect } from "react"
import { cadastrarMusica, listarArtistas, listarGeneros, listarAlbuns } from "../../services/api"
import { BotaoPrimario } from "../../components/BotaoPrimario/BotaoPrimario"
import { useNavigate } from "react-router-dom"
import styles from './CadastroMusica.module.css'

export function CadastroMusica() {
  const [carregando, setCarregando] = useState(false)
  const [erro, setErro] = useState(null)
  const [generos, setGeneros] = useState([])
  const [artistas, setArtistas] = useState([])
  const [albuns, setAlbuns] = useState([])
  const [dados, setDados] = useState({
    titulo: "",
    duracao: "",
    caminhoAudio: "",
    idArtista: null,
    idAlbum: null,
    idGenero: null
  })

  const navegar = useNavigate()

  const redirecionarMusicas = () => {
    navegar('/musicas')
  }

  useEffect(() => {
    setCarregando(true)

    listarGeneros()
    .then(resposta => {
      setGeneros(resposta.data)
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })

    listarArtistas()
    .then(resposta => {
      setArtistas(resposta.data)
      
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })
    .finally(() => {
      setCarregando(false)
    })

  }, [])

  function aoSubmeter(evento) {
    evento.preventDefault()
    setCarregando(true)

    const corpo = { ...dados, idUsuario: localStorage.getItem('ID_USUARIO') }

    cadastrarMusica(corpo)
    .then(resposta => {
      redirecionarMusicas()
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })
    .finally(() => {
      setCarregando(false)
    })
  }

  return (
    <main className={styles.container}>
      <div className={styles.card}>
        <header>
          <h2>Cadastro Música</h2>
          <p>Insira as informações da música para cadastra-la</p>
        </header>

        <form onSubmit={aoSubmeter}>
          <div className={styles.campos}>
            <div className={styles.campo}>
              <input type="text" name="titulo" id="titulo" placeholder="Título" onChange={(evento) => setDados({...dados, titulo: evento.target.value }) } />
            </div>

            <div className={styles.campo}>
              <input type="number" name="duracao" id="duracao" min={0} placeholder="Duração (ex: 120s)" onChange={(evento) => setDados({...dados, duracao: Number(evento.target.value) })} />
            </div>

            <div className={styles.campo}>
              <input type="text" name="caminhoAudio" id="caminhoAudio" placeholder="Caminho do áudio" onChange={(evento) => setDados({...dados, caminhoAudio: evento.target.value }) } />
            </div>

            <div className={styles.campo}>
              <select 
                name="genero" 
                id="genero"
                onChange={(evento) => setDados({...dados, idGenero: evento.target.value })}
                >
                <option value="">Gênero</option>
                {generos.map(genero => (
                  <option key={genero.idGenero} value={genero.idGenero}>{genero.nome}</option>
                ))}
              </select>
            </div>

            <div className={styles.campo}>
              <select 
                name="artista" 
                id="artista"
                onChange={(evento) => {
                  const idArtistaEscolhido = evento.target.value
                  setDados({...dados, idArtista: evento.target.value, idAlbum: null })

                  if (idArtistaEscolhido) {
                    listarAlbuns(idArtistaEscolhido)
                    .then(resposta => setAlbuns(resposta.data))
                    .catch(erro => setErro(erro.response.data.mensagem))
                  } else {
                    setAlbuns([])
                  }
                }}  
              >
                <option value="">Artista</option>
                {artistas.map(artista => (
                  <option key={artista.idArtista} value={artista.idArtista}>{artista.nome}</option>
                ))}
              </select>
            </div>

            <div className={styles.campo}>
              <select 
                name="album" 
                id="album"
                onChange={(evento) => setDados({...dados, idAlbum: evento.target.value })}  
              >
                <option value="">Álbum</option>
                {albuns.map(album => (
                  <option key={album.idAlbum} value={album.idAlbum}>{album.titulo}</option>
                ))}
              </select>
            </div>
          </div>

          {erro && <p className={styles.erro}>{erro}</p>}

          <BotaoPrimario type="submit" disabled={carregando}>
            { carregando ? 'Cadastrando' : 'Cadastrar' }
          </BotaoPrimario>
        </form>
      </div>
    </main>
  )
}