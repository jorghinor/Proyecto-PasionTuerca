import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Carousel from './Carousel';

function Login({onLogin}) {
  const [user, setUser] = useState('');
  const [pass, setPass] = useState('');
  const [err, setErr] = useState('');
  async function submit(e){
    e.preventDefault();
    try{
      const res = await axios.post('/api/v1/auth/login', {username:user, password:pass});
      localStorage.setItem('token', res.data.accessToken);
      onLogin();
    }catch(er){
      setErr('Credenciales invalidas');
    }
  }
  return (
    <form onSubmit={submit} className="space-y-2">
      <input className="p-2 rounded w-full text-black" placeholder="usuario" value={user} onChange={e=>setUser(e.target.value)} />
      <input className="p-2 rounded w-full text-black" placeholder="password" type="password" value={pass} onChange={e=>setPass(e.target.value)} />
      <div className="flex items-center gap-2">
        <button className="button" type="submit">Login</button>
        {err && <span className="text-red-400">{err}</span>}
      </div>
    </form>
  );
}

function Upload() {
  const [file, setFile] = useState(null);
  const [title, setTitle] = useState('');
  const [msg, setMsg] = useState('');
  async function submit(e){
    e.preventDefault();
    const token = localStorage.getItem('token');
    if(!token){ setMsg('Login required'); return; }
    const fd = new FormData();
    fd.append('file', file);
    fd.append('title', title);
    try{
      await axios.post('/api/v1/media', fd, { headers: { Authorization: 'Bearer '+token, 'Content-Type': 'multipart/form-data' }});
      setMsg('Uploaded');
    }catch(er){
      setMsg('Error uploading');
    }
  }
  return (
    <form onSubmit={submit} className="space-y-2 mt-4">
      <input type="file" onChange={e=>setFile(e.target.files[0])} />
      <input className="p-2 rounded w-full text-black" placeholder="title" value={title} onChange={e=>setTitle(e.target.value)} />
      <button className="button" type="submit">Upload</button>
      {msg && <div>{msg}</div>}
    </form>
  );
}

export default function App(){
  const [authed, setAuthed] = useState(!!localStorage.getItem('token'));

  return (
    <>
      <div className="glowing-border mb-8">
        <Carousel />
      </div>
      <div className="glowing-border">
        {authed ? <Upload /> : <Login onLogin={()=>setAuthed(true)} />}
      </div>
    </>
  )
}