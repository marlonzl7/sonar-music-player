import { useState } from 'react'
import { useEffect } from 'react'
import { BotaoPrimario } from '../../components/BotaoPrimario/BotaoPrimario'
import { BotaoSecundario } from '../../components/BotaoSecundario/BotaoSecundario'
import styles from './Login.module.css'
import { login } from '../../services/api'
import { useNavigate } from 'react-router-dom'

export function Login() {
  const [carregando, setCarregando] = useState(false)
  const [erro, setErro] = useState(null)
  const [dados, setDados] = useState({
    email: ""
  })

  const navegar = useNavigate()

  const redirecionarMusicas = () => {
    navegar('/musicas')
  }

  function aoSubmeter(evento) {
    evento.preventDefault()
    setCarregando(true)

    login(dados.email)
    .then(resposta => {
      localStorage.setItem("ID_USUARIO", resposta.data.idUsuario)

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
          <h2>Login</h2>
          <p>Insira seu email para entrar</p>
        </header>

        <form onSubmit={aoSubmeter}>
          <div className={styles.inputs}>
            <div className={styles.input}>
              <input type="email" name="email" id="email" placeholder='Email' onChange={(evento) => setDados({...dados, email: evento.target.value})} />
            </div>
          </div>

          {erro && <p className={styles.erro}>{erro}</p>}

          <BotaoPrimario type='submit' disabled={carregando}>
            { carregando ? 'Entrando...' : 'Entrar'}
          </BotaoPrimario>
        </form>

        <footer>
          <span>Não tem uma conta?</span>
          <BotaoSecundario type='button' onClick={() => navegar('/cadastro')} disabled={carregando}>
            Cadastrar
          </BotaoSecundario>
        </footer>
      </div>
    </main>
  )
}