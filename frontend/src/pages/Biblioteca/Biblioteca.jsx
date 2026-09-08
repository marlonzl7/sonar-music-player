import { useEffect, useState } from "react"
import { listarGeneros, listarMusicas } from '../../services/api'
import { CardMusica } from "../../components/CardMusica/CardMusica"
import { BotaoSecundario } from "../../components/BotaoSecundario/BotaoSecundario"
import { useNavigate } from "react-router-dom"
import styles from './Biblioteca.module.css'

export function Biblioteca() {
  const [carregando, setCarregando] = useState(false)
  const [musicas, setMusicas] = useState([])
  const [busca, setBusca] = useState("")
  const [generos, setGeneros] = useState([])
  const [generoSelecionado, setGeneroSelecionado] = useState('')

  const navegar = useNavigate()

  useEffect(() => {
    setCarregando(true)

    listarGeneros()
    .then(resposta => {
      setGeneros(resposta.data)
    })
    .catch(erro => {
      
    })
    .finally(() => {
      setCarregando(false)
    })

  }, [])

  useEffect(() => {
    setCarregando(true)

    listarMusicas(localStorage.getItem('ID_USUARIO'), { 
      titulo: busca, 
      idGenero: generoSelecionado || undefined 
    })
    .then(resposta => {
      setMusicas(resposta.data)
    })
    .catch(erro => {

    })
    .finally(() => {
      setCarregando(false)
    })

  }, [busca, generoSelecionado])

  return (
    <main className={styles.container}>
      <header>
        <h2>Biblioteca</h2>
        <p>Suas músicas cadastradas</p>
      </header>

      <section className={styles.filtros}>
        <input type="search" placeholder="Busque por um título..." onChange={(evento) => setBusca(evento.target.value)} />

        <select 
          name="generos" 
          id="generos"
          value={generoSelecionado} 
          onChange={(evento) => setGeneroSelecionado(evento.target.value)}
        >
          <option value="">Todos os gêneros</option>
          {generos.map(genero => (
            <option key={genero.idGenero} value={genero.idGenero}>{genero.nome}</option>
          ))}
        </select>
      </section>

      <section className={styles.acoes}>
        <BotaoSecundario type="button" onClick={() => navegar('/musicas/nova')} >Nova Música</BotaoSecundario>
      </section>

      <section className={styles.lista}>
        {musicas.length === 0 && !carregando && <p className={styles.mensagemVazia}>Nenhuma música cadastrada</p>}
        {musicas.map(musica => <CardMusica key={musica.idMusica} musica={musica} />)}        
      </section>
    </main>
  )
}