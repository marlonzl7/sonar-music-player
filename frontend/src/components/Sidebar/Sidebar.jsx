import { NavLink, Outlet } from 'react-router-dom'
import styles from './Sidebar.module.css'

export function Sidebar() {
    return (
        <div className={styles.container}>
            <aside className={styles.sidebar}>
                <p className={styles.brand}>Sonar</p>
                <NavLink to="/musicas" className={({ isActive }) => isActive ? styles.itemAtivo : styles.item}>
                    Músicas
                </NavLink>
                <NavLink to="/playlists" className={({ isActive }) => isActive ? styles.itemAtivo : styles.item}>
                    Playlists
                </NavLink>
            </aside>
            <main className={styles.main}>
                <div className={styles.content}>
                    <Outlet />
                </div>
            </main>
        </div>
    )
}