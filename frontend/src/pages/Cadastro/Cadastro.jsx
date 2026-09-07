import styles from './Cadastro.module.css'
import { BotaoSecundario } from '../../components/BotaoSecundario/BotaoSecundario'
import { BotaoPrimario } from '../../components/BotaoPrimario/BotaoPrimario'
import { useState } from 'react'
import { cadastrarUsuario } from '../../services/api'
import { useNavigate } from 'react-router-dom'

export function Cadastro() {
  const [carregando, setCarregando] = useState(false)
  const [erro, setErro] = useState(null)
  const [dados, setDados] = useState({
    nome: "",
    email: ""
  })

  const navegar = useNavigate()

  const redirecionarLogin = () => {
    navegar('/login')
  }

  function aoSubmeter(evento) {
    evento.preventDefault()
    setCarregando(true)

    cadastrarUsuario(dados)
    .then(resposta => {      
      redirecionarLogin()
    })
    .catch(erro => {
      setErro(erro.response.data.mensagem)
    })
    .finally(() => {
      setCarregando(false)
    });
  }

  return (
    <main className={styles.container}>
      <div className={styles.card}>
        <header>
          <h2>Cadastro</h2>
          <p>Insira suas informações para criar sua conta</p>
        </header>

        <form onSubmit={aoSubmeter}>
          <div className={styles.inputs}>
            <div className={styles.input}>
              <input type="text" name="nome" id="nome" placeholder='Nome' onChange={(evento) => setDados({...dados, nome: evento.target.value})} />
            </div>

            <div className={styles.input}>
              <input type="email" name="email" id="email" placeholder='Email' onChange={(evento) => setDados({...dados, email: evento.target.value})} />
            </div>
          </div>

          {erro && <p className={styles.erro}>{erro}</p>}

          <BotaoPrimario type='submit' disabled={carregando}>
            { carregando ? 'Cadastrando...' : 'Cadastrar' }
          </BotaoPrimario>
        </form>

        <footer>
          <span>Já possui uma conta?</span>
          <BotaoSecundario type='button' onClick={() => navegar('/login')} disabled={carregando}>
            Entrar
          </BotaoSecundario>
        </footer>
      </div>
    </main>
  )
}