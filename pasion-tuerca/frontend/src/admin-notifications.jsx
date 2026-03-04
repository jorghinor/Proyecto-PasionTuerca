import React, {useState} from 'react';
import axios from 'axios';

export default function AdminNotifications(){
  const [title, setTitle] = useState('');
  const [message, setMessage] = useState('');
  const [scheduledAt, setScheduledAt] = useState('');
  const [msg, setMsg] = useState('');

  async function schedule(e){
    e.preventDefault();
    setMsg('');
    const token = localStorage.getItem('token');
    if (!token) {
      setMsg('Authentication required.');
      return;
    }
    if (!title || !message || !scheduledAt) {
      setMsg('All fields are required.');
      return;
    }

    try {
      const isoScheduledAt = new Date(scheduledAt).toISOString();
      await axios.post('/api/v1/notifications', { title, message, scheduledAt: isoScheduledAt }, { headers: { Authorization: 'Bearer '+token }});
      setMsg(`Notification scheduled for ${new Date(scheduledAt).toLocaleString()}`);
      setTitle('');
      setMessage('');
      setScheduledAt('');
    } catch (error) {
      setMsg('Error scheduling notification.');
      console.error(error);
    }
  }

  return (
    <div className="glowing-border text-white">
      <h3 className="text-2xl font-bold mb-4">Schedule Notification</h3>
      <form onSubmit={schedule} className="space-y-4 p-4 bg-gray-800 rounded-lg">
        <div>
          <label htmlFor="title" className="block text-gray-300 text-sm font-bold mb-2">Title</label>
          <input id="title" className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline" placeholder="Notification Title" value={title} onChange={e=>setTitle(e.target.value)} />
        </div>
        <div>
          <label htmlFor="message" className="block text-gray-300 text-sm font-bold mb-2">Message</label>
          <textarea id="message" className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline" placeholder="Notification Message" value={message} onChange={e=>setMessage(e.target.value)} />
        </div>
        <div>
          <label htmlFor="scheduledAt" className="block text-gray-300 text-sm font-bold mb-2">Schedule At</label>
          <input id="scheduledAt" type="datetime-local" value={scheduledAt} onChange={e=>setScheduledAt(e.target.value)} className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline" />
        </div>
        <button className="button" type="submit">Schedule Notification</button>
        {msg && <p className="mt-2 text-cyan-400">{msg}</p>}
      </form>
    </div>
  )
}
