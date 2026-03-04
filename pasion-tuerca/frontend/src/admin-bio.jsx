import React, {useEffect, useState} from 'react';
import axios from 'axios';

export default function AdminBio(){
  const [bio, setBio] = useState('');
  const [msg, setMsg] = useState('');

  useEffect(()=>{
    axios.get('/api/v1/biography').then(r=> {
      if(r.data.length) setBio(r.data[0].content);
    })
  }, []);

  async function save(e){
    e.preventDefault();
    setMsg('');
    const token = localStorage.getItem('token');
    if (!token) {
      setMsg('Authentication required');
      return;
    }
    try {
      await axios.put('/api/v1/biography', { content: bio }, { headers: { Authorization: 'Bearer '+token }});
      setMsg('Biography saved successfully!');
    } catch (error) {
      setMsg('Error saving biography.');
      console.error(error);
    }
  }

  return (
    <div className="glowing-border text-white">
      <h3 className="text-2xl font-bold mb-4">Edit Biography</h3>
      <form onSubmit={save}>
        <textarea
          className="w-full p-2 bg-gray-800 rounded-md border border-gray-700 focus:outline-none focus:ring-2 focus:ring-cyan-500"
          value={bio}
          onChange={e=>setBio(e.target.value)}
          rows="10"
        />
        <button className="button mt-4" type="submit">Save Biography</button>
        {msg && <p className="mt-4 text-cyan-400">{msg}</p>}
      </form>
    </div>
  )
}
