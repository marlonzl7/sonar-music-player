import styles from './BotaoSecundario.module.css'

export function BotaoSecundario({ type = 'button', onClick, disabled, children }) {
  return (
    <button className={styles.botao} type={type} onClick={onClick} disabled={disabled}>
      {children}
    </button>
  )
}