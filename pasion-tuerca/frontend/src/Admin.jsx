import React from 'react';
import { Link, Outlet } from 'react-router-dom';

export default function Admin(){
  return (
    <div className="glowing-border">
      <h2 className="text-2xl font-bold mb-4">Admin Panel</h2>
      <nav className="flex gap-4 flex-wrap">
        <Link to="media" className="button">Media</Link>
        <Link to="calendar" className="button">Calendar</Link>
        <Link to="notifications" className="button">Notifications</Link>
        <Link to="biography" className="button">Biography</Link>
        <Link to="carousel" className="button">Carousel</Link>
        <Link to="competitions" className="button">Competitions</Link>
      </nav>
      <div className="mt-4 p-4 border border-gray-700 rounded">
        <Outlet />
      </div>
    </div>
  )
}
