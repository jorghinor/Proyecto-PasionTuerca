import React from 'react';
import { Link, Outlet } from 'react-router-dom';

export default function MainLayout() {
  const currentYear = new Date().getFullYear();
  return (
    <div className="container mx-auto flex flex-col min-h-screen">
      <header className="app-header p-4 shadow-md glowing-green-shadow">
        <div className="flex justify-between items-center">
          <h1 id="animated-title" className="text-3xl font-bold">Pasion Futbol</h1>
          <div>
            <Link to="/" className='button mr-4'>Home</Link>
            <Link to="/admin" className='button'>Admin Panel</Link>
          </div>
        </div>
      </header>

      <main className="flex-grow p-4">
        <Outlet />
      </main>

      <footer className="app-footer p-4 text-center mt-auto glowing-green-shadow">
        <p className="footer-glow">&copy; {currentYear} Pasion Futbol. Todos los derechos reservados.</p>
      </footer>
    </div>
  );
}