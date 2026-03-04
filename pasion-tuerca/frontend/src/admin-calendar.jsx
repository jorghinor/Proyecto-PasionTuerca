import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AdminCalendar = () => {
  const [events, setEvents] = useState([]);
  const [newEvent, setNewEvent] = useState({
    date: '',
    title: '',
    description: '',
    imageUrl: '',
    videoUrl: '',
    commentBoxEnabled: false,
    maxAssetsPerDay: 1,
    itemOrder: 0,
  });
  const [editingEvent, setEditingEvent] = useState(null);
  const [selectedEventComments, setSelectedEventComments] = useState([]);
  const [newCommentText, setNewCommentText] = useState('');
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchEvents();
  }, []);

  const fetchEvents = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('/api/v1/calendar/admin', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setEvents(response.data);
    } catch (error) {
      console.error('Error fetching calendar events:', error);
      setError('Error fetching calendar events. Please try again later.');
    }
  };

  const fetchCommentsForEvent = async (eventId) => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get(`/api/v1/calendar/admin/${eventId}/comments`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setSelectedEventComments(response.data);
    } catch (error) {
      console.error('Error fetching comments for event:', error);
      setSelectedEventComments([]);
      setError('Error fetching comments for event.');
    }
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setNewEvent((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleEditInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setEditingEvent((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleAddEvent = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      await axios.post('/api/v1/calendar/admin', newEvent, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setNewEvent({ date: '', title: '', description: '', imageUrl: '', videoUrl: '', commentBoxEnabled: false, maxAssetsPerDay: 1, itemOrder: 0 });
      fetchEvents();
      setError(null);
    } catch (error) {
      console.error('Error adding calendar event:', error);
      setError('Error adding calendar event. Please check the form and try again.');
    }
  };

  const handleUpdateEvent = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      await axios.put(`/api/v1/calendar/admin/${editingEvent.id}`, editingEvent, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setEditingEvent(null);
      fetchEvents();
      setError(null);
    } catch (error) {
      console.error('Error updating calendar event:', error);
      setError('Error updating calendar event. Please try again.');
    }
  };

  const handleDeleteEvent = async (id) => {
    if (!window.confirm('Are you sure you want to delete this event?')) return;
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`/api/v1/calendar/admin/${id}`, { headers: { Authorization: `Bearer ${token}` } });
      fetchEvents();
      setError(null);
    } catch (error) {
      console.error('Error deleting calendar event:', error);
      setError('Error deleting calendar event. Please try again.');
    }
  };

  const handleAddComment = async (eventId) => {
    if (newCommentText.length < 20) {
      alert('Comment must be at least 20 characters long.');
      return;
    }
    try {
      const token = localStorage.getItem('token');
      await axios.post(`/api/v1/calendar/admin/${eventId}/comments`, { commentText: newCommentText }, { headers: { Authorization: `Bearer ${token}` } });
      setNewCommentText('');
      fetchCommentsForEvent(eventId);
      setError(null);
    } catch (error) {
      console.error('Error adding comment:', error);
      setError('Error adding comment. Please try again.');
    }
  };

  const handleDeleteComment = async (commentId, eventId) => {
    if (!window.confirm('Are you sure you want to delete this comment?')) return;
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`/api/v1/calendar/admin/comments/${commentId}`, { headers: { Authorization: `Bearer ${token}` } });
      fetchCommentsForEvent(eventId);
      setError(null);
    } catch (error) {
      console.error('Error deleting comment:', error);
      setError('Error deleting comment. Please try again.');
    }
  };

  const eventData = editingEvent || newEvent;
  const eventChangeHandler = editingEvent ? handleEditInputChange : handleInputChange;

  return (
    <div className="glowing-border text-white">
      {error && <div className="text-red-500 mb-4">{error}</div>}
      <h1 className="text-2xl font-bold mb-4">Admin Calendar</h1>

      <form onSubmit={editingEvent ? handleUpdateEvent : handleAddEvent} className="mb-8 p-4 bg-gray-800 rounded-lg">
        <h2 className="text-xl font-semibold mb-4">{editingEvent ? 'Edit Event' : 'Add New Event'}</h2>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
          <div>
            <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="date">Date:</label>
            <input
              type="date"
              id="date"
              name="date"
              value={eventData.date}
              onChange={eventChangeHandler}
              className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
          <div>
            <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="title">Title:</label>
            <input
              type="text"
              id="title"
              name="title"
              placeholder="Title"
              value={eventData.title}
              onChange={eventChangeHandler}
              className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
          <div className="md:col-span-2">
            <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="description">Description:</label>
            <textarea
              id="description"
              name="description"
              placeholder="Description"
              value={eventData.description}
              onChange={eventChangeHandler}
              className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
          <div>
            <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="imageUrl">Image URL:</label>
            <input
              type="text"
              id="imageUrl"
              name="imageUrl"
              placeholder="Image URL"
              value={eventData.imageUrl}
              onChange={eventChangeHandler}
              className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
          <div>
            <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="videoUrl">Video URL:</label>
            <input
              type="text"
              id="videoUrl"
              name="videoUrl"
              placeholder="Video URL"
              value={eventData.videoUrl}
              onChange={eventChangeHandler}
              className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
          <div className="flex items-center">
            <input
              type="checkbox"
              name="commentBoxEnabled"
              checked={eventData.commentBoxEnabled}
              onChange={eventChangeHandler}
              className="mr-2"
            />
            <label>Enable Comments</label>
          </div>
          <div>

            <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="maxAssetsPerDay">Max Assets Per Day:</label>
            <input
              type="number"
              id="maxAssetsPerDay"
              name="maxAssetsPerDay"
              placeholder="Max Assets Per Day"
              value={eventData.maxAssetsPerDay}
              onChange={eventChangeHandler}
              className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
          <div>
            <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="itemOrder">Item Order:</label>
            <input
              type="number"
              id="itemOrder"
              name="itemOrder"
              placeholder="Item Order"
              value={eventData.itemOrder}
              onChange={eventChangeHandler}
              className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            />
          </div>
        </div>
        <button type="submit" className="button">
          {editingEvent ? 'Update Event' : 'Add Event'}
        </button>
        {editingEvent && (
          <button type="button" onClick={() => setEditingEvent(null)} className="button bg-gray-600 hover:bg-gray-700 ml-2">
            Cancel
          </button>
        )}
      </form>

      <h2 className="text-xl font-semibold mb-4">Existing Events</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {events.map((event) => (
          <div key={event.id} className="bg-gray-800 rounded-lg shadow-md p-4">
            {/* Event details */}
            <div className="mt-4">
              <button onClick={() => { setEditingEvent({ ...event }); fetchCommentsForEvent(event.id); }} className="button bg-yellow-500 hover:bg-yellow-600">Edit</button>
              <button onClick={() => handleDeleteEvent(event.id)} className="button bg-red-600 hover:bg-red-800 ml-2">Delete</button>
            </div>
            {editingEvent && editingEvent.id === event.id && event.commentBoxEnabled && (
              <div className="mt-4 p-2 border-t border-gray-700 pt-2">
                <h4 className="font-semibold">Comments</h4>
                {/* Comments section */}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default AdminCalendar;