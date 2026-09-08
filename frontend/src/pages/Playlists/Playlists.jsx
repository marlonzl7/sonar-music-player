import { useEffect, useState } from "react"
import { cadastrarPlaylist, listarPlaylistsDoUsuario } from "../../services/api"
import { BotaoSecundario } from "../../components/BotaoSecundario/BotaoSecundario"
import { BotaoPrimario } from "../../components/BotaoPrimario/BotaoPrimario"
import { useNavigate } from "react-router-dom"
import { CardPlaylist } from "../../components/CardPlaylist/CardPlaylist"
import styles from "./Playlists.module.css"

export function Playlists() {
  const [carregando, setCarregando] = useState(false)
  const [erro, setErro] = useState(null)
  const [playlists, setPlaylists] = useState([])
  const [mostrarFormulario, setMostrarFormulario] = useState(false)
  const [nomeNovaPlaylist, setNomeNovaPlaylist] = useState("")

  const navegar = useNavigate()

  function buscarPlaylists() {
    setCarregando(true)

    listarPlaylistsDoUsuario(localStorage.getItem('ID_USUARIO'))
      .then(resposta => {
        setPlaylists(resposta.data)
      })
      .catch(erro => {
        setErro(erro.response.data.mensagem)
      })
      .finally(() => {
        setCarregando(false)
      })
  }

  function aoCriarPlaylist(evento) {
    evento.preventDefault()
    setCarregando(true)

    cadastrarPlaylist({ idUsuario: localStorage.getItem('ID_USUARIO'), nome: nomeNovaPlaylist })
    .then(resposta => {
      setNomeNovaPlaylist("")
      setMostrarFormulario(false)
      buscarPlaylists()
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })
    .finally(() => {
      setCarregando(false)
    })
  }

  useEffect(() => {
    buscarPlaylists()
  }, [])

  return (
    <main className={styles.container}>
      <header>
        <h2>Playlists</h2>
        <p>Organize suas músicas favoritas</p>
      </header>

      <section className={styles.acoes}>
        <BotaoSecundario type="button" onClick={() => setMostrarFormulario(!mostrarFormulario)}>
          Nova playlist
        </BotaoSecundario>
      </section>

      {mostrarFormulario && (
        <form className={styles.formulario} onSubmit={aoCriarPlaylist}>
          <div className={styles.formularioLinha}>
            <input
              type="text"
              placeholder="Nome da playlist"
              value={nomeNovaPlaylist}
              onChange={(evento) => setNomeNovaPlaylist(evento.target.value)}
            />
            <BotaoPrimario type="submit">Criar</BotaoPrimario>
          </div>

          {erro && <p className={styles.erro}>{erro}</p>}
        </form>
      )}

      <section>
        {playlists.length === 0 && !carregando && <p className={styles.mensagemVazia}>Nenhuma playlist cadastrada</p>}
        {playlists.map(playlist => <CardPlaylist key={playlist.idPlaylist} playlist={playlist} onClick={() => navegar('/playlists/' + playlist.idPlaylist)}></CardPlaylist>)}
      </section>
    </main>
  )
}