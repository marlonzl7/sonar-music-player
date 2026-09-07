import { createBrowserRouter, Navigate } from 'react-router-dom'
import { Sidebar } from './components/Sidebar/Sidebar'
import { Login } from './pages/Login/Login'
import { Cadastro } from './pages/Cadastro/Cadastro'
import { Biblioteca } from './pages/Biblioteca/Biblioteca'
import { CadastroMusica } from './pages/CadastroMusica/CadastroMusica'
import { Playlists } from './pages/Playlists/Playlists'
import { PlaylistDetalhe } from './pages/PlaylistDetalhe/PlaylistDetalhe'

export const router = createBrowserRouter([
  { path: '/login', element: <Login /> },
  { path: '/cadastro', element: <Cadastro /> },
  {
    path: '/',
    element: <Sidebar />,
    children: [
      { index: true, element: <Navigate to="/login" replace /> },
      { path: 'musicas', element: <Biblioteca /> },
      { path: 'musicas/nova', element: <CadastroMusica /> },
      { path: 'playlists', element: <Playlists /> },
      { path: 'playlists/:id', element: <PlaylistDetalhe /> }
    ]
  }
])