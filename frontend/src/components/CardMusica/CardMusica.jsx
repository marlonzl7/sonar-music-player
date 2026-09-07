import styles from './CardMusica.module.css'

export function CardMusica({ musica }) {
  function formatarDuracao(segundos) {
    const minutos = Math.floor(segundos / 60)
    const segundosRestantes = segundos % 60
    return `${minutos}:${String(segundosRestantes).padStart(2, '0')}`
  }

  return (
    <article className={styles.card}>
      <p className={styles.titulo}>{musica.titulo}</p>
      <p className={styles.meta}>{musica.artista.nome} · {musica.genero.nome} · {formatarDuracao(musica.duracao)}</p>
    </article>
  )
}