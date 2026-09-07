import styles from './BotaoPrimario.module.css'

export function BotaoPrimario({ type = 'button', onClick, disabled, children }) {
  return (
    <button className={styles.botao} type={type} onClick={onClick} disabled={disabled}>
      {children}
    </button>
  )
}