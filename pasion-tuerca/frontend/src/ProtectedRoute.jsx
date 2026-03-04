import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

const ProtectedRoute = () => {
  const token = localStorage.getItem('token');

  // If there's no token, redirect to the login page (home page in this case)
  if (!token) {
    // You can also decode the token here and check for expiration or roles
    return <Navigate to="/" replace />;
  }

  // If token exists, render the child route
  return <Outlet />;
};

export default ProtectedRoute;
