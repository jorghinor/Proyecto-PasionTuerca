import React, {useEffect, useState} from 'react';
import axios from 'axios';

export default function AdminMedia(){
  const [items, setItems] = useState([]);
  const [file, setFile] = useState(null);
  const [title, setTitle] = useState('');
  const [msg, setMsg] = useState('');

  useEffect(() => {
    fetchData();
  }, []);

  async function fetchData() {
    setMsg('');
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('/api/v1/media', { 
        headers: { Authorization: `Bearer ${token}` }
      }); 
      setItems(response.data);
    } catch (error) {
      console.error("Error fetching media:", error);
      setMsg('Could not fetch media.');
    }
  }

  async function upload(e){
    e.preventDefault();
    setMsg('');
    const token = localStorage.getItem('token');
    if (!token) {
      setMsg('Authentication required.');
      return;
    }
    if (!file) {
      setMsg('Please select a file to upload.');
      return;
    }

    const fd = new FormData();
    fd.append('file', file);
    fd.append('title', title);

    try {
      await axios.post('/api/v1/media', fd, { headers: { Authorization: 'Bearer '+token }});
      setMsg('Upload successful!');
      setTitle('');
      setFile(null);
      document.getElementById('file-input').value = ''; // Clear file input
      fetchData(); // Refresh the media list
    } catch (error) {
      setMsg('Error uploading file.');
      console.error(error);
    }
  }

  return (
    <div className="glowing-border text-white">
      <h3 className="text-2xl font-bold mb-4">Manage Media</h3>
      
      <form onSubmit={upload} className="space-y-4 p-4 bg-gray-800 rounded-lg mb-8">
        <div>
          <label htmlFor="file-input" className="block text-gray-300 text-sm font-bold mb-2">Upload New Media</label>
          <input id="file-input" type="file" onChange={e=>setFile(e.target.files[0])} className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline file:mr-4 file:py-2 file:px-4 file:rounded-full file:border-0 file:text-sm file:font-semibold file:bg-cyan-50 file:text-cyan-700 hover:file:bg-cyan-100"/>
        </div>
        <div>
          <label htmlFor="title-input" className="block text-gray-300 text-sm font-bold mb-2">Title</label>
          <input id="title-input" className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline" placeholder="Title for the media" value={title} onChange={e=>setTitle(e.target.value)} />
        </div>
        <button className="button" type="submit">Upload</button>
        {msg && <p className="mt-2 text-cyan-400">{msg}</p>}
      </form>

      <h3 className="text-xl font-bold mb-4">Uploaded Media</h3>
      <div className="mt-4 grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-4">
        {items.map(it=> (
          <div key={it.id} className="p-2 bg-gray-800 rounded-lg shadow-lg">
            <img src={it.url} alt={it.title} className="w-full h-24 object-cover rounded-md"/>
            <div className="text-sm mt-2 truncate" title={it.title}>{it.title}</div>
          </div>
        ))}
      </div>
    </div>
  )
}
