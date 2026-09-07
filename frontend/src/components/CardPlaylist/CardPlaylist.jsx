import styles from './CardPlaylist.module.css'

export function CardPlaylist({ playlist, onClick }) {
  return (
    <article className={styles.card} onClick={onClick}>
      <p className={styles.nome}>{playlist.nome}</p>
      <p className={styles.total}>{`${playlist.totalMusicas} música${playlist.totalMusicas === 1 ? '' : 's'}`}</p>
    </article>
  )
}