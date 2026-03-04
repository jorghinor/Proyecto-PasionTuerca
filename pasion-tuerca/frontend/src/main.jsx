import React from 'react'
import { createRoot } from 'react-dom/client'
import App from './App'
import './styles.css'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import MainLayout from './MainLayout'
import ProtectedRoute from './ProtectedRoute' // Import the guard
import Admin from './Admin'
import AdminMedia from './admin-media'
import AdminCalendar from './admin-calendar'
import AdminNotifications from './admin-notifications'
import AdminBio from './admin-bio'
import AdminCarousel from './admin-carousel'
import AdminCompetitions from './admin-competitions'

createRoot(document.getElementById('root')).render(
  <BrowserRouter>
    <Routes>
      <Route element={<MainLayout />}>
        {/* Public Route */}
        <Route path='/' element={<App />} />

        {/* Protected Admin Routes */}
        <Route element={<ProtectedRoute />}>
          <Route path='/admin' element={<Admin />}>
            <Route path='media' element={<AdminMedia />} />
            <Route path='calendar' element={<AdminCalendar />} />
            <Route path='notifications' element={<AdminNotifications />} />
            <Route path='biography' element={<AdminBio />} />
            <Route path='carousel' element={<AdminCarousel />} />
            <Route path='competitions' element={<AdminCompetitions />} />
          </Route>
        </Route>
      </Route>
    </Routes>
  </BrowserRouter>
)
