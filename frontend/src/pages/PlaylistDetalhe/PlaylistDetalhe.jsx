import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { adicionarMusicaNaPlaylist, listarMusicas, obterPlaylist, removerMusicaDaPlaylist } from '../../services/api'
import { BotaoSecundario } from "../../components/BotaoSecundario/BotaoSecundario"
import styles from "./PlaylistDetalhe.module.css"

export function PlaylistDetalhe() {
  const { id } = useParams()
  
  const [carregando, setCarregando] = useState(false)
  const [erro, setErro] = useState(null)
  const [playlist, setPlaylist] = useState(null)
  const [musicasDisponiveis, setMusicasDisponiveis] = useState([])
  const [musicaSelecionada, setMusicaSelecionada] = useState(null)

  const navegar = useNavigate()

  useEffect(() => {
    buscarPlaylist()

    listarMusicas(localStorage.getItem('ID_USUARIO'))
    .then(resposta => {
      setMusicasDisponiveis(resposta.data)
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })
    .finally(() => {
      setCarregando(false)
    })

  }, [])

  function buscarPlaylist() {
    setCarregando(true)

    obterPlaylist(id, localStorage.getItem('ID_USUARIO'))
    .then(resposta => {
      setPlaylist(resposta.data)
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })
    .finally(() => {
      setCarregando(false)
    })
  }

  function aoAdicionarMusica(evento) {
    evento.preventDefault()

    if (!musicaSelecionada) return

    adicionarMusicaNaPlaylist(id, {
      idMusica: musicaSelecionada,
      idUsuario: localStorage.getItem('ID_USUARIO')
    })
    .then(resposta => {
      setMusicaSelecionada(null)
      buscarPlaylist()
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })
  }

  function aoRemoverMusica(idMusica) {
    removerMusicaDaPlaylist(id, idMusica, localStorage.getItem('ID_USUARIO'))
    .then(resposta => {
      buscarPlaylist()
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })
  }

  return (
    <main className={styles.container}>
      <button className={styles.voltar} onClick={() => navegar('/playlists')}>Voltar</button>

      {playlist && (
        <>
          <header>
            <h2>{playlist.nome}</h2>
          </header>

          <form className={styles.formulario} onSubmit={aoAdicionarMusica}>
            <select
              value={musicaSelecionada || ''}
              onChange={(evento) => setMusicaSelecionada(evento.target.value)}
            >
              <option value="">Escolha uma música</option>
              {musicasDisponiveis.map(musica => (
                <option key={musica.idMusica} value={musica.idMusica}>{musica.titulo}</option>
              ))}
            </select>
            <BotaoSecundario type="submit">Adicionar música</BotaoSecundario>
          </form>

          {erro && <p className={styles.erro}>{erro}</p>}

          <section>
            {playlist.musicas.length === 0 && <p className={styles.mensagemVazia}>Nenhuma música na playlist</p>}
            {playlist.musicas.map((musica, indice) => (
              <div key={musica.idMusica} className={styles.item}>
                <p>{indice + 1}. {musica.titulo}</p>
                <button onClick={() => aoRemoverMusica(musica.idMusica)}>Remover</button>
              </div>
            ))}
          </section>
        </>
      )}
    </main>
  )
}