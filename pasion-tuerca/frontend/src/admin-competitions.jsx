import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FormInput = ({ label, name, value, onChange, type = 'text' }) => (
  <div className="mb-4">
    <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor={name}>
      {label}:
    </label>
    {type === 'textarea' ? (
      <textarea
        id={name}
        name={name}
        value={value}
        onChange={onChange}
        className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
        rows="4"
      />
    ) : (
      <input
        type={type}
        id={name}
        name={name}
        value={value}
        onChange={onChange}
        className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
        required={name === 'title'}
      />
    )}
  </div>
);

const AdminCompetitions = () => {
  const [competitions, setCompetitions] = useState([]);
  const [newCompetition, setNewCompetition] = useState({
    title: '',
    description: '',
    contractorInfo: '',
    imageUrl: '',
    videoUrl: '',
  });
  const [editingCompetition, setEditingCompetition] = useState(null);

  useEffect(() => {
    fetchCompetitions();
  }, []);

  const fetchCompetitions = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('/api/v1/competitions/admin', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCompetitions(response.data);
    } catch (error) {
      console.error('Error fetching competitions:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewCompetition((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleEditInputChange = (e) => {
    const { name, value } = e.target;
    setEditingCompetition((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleAddCompetition = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      await axios.post('/api/v1/competitions/admin', newCompetition, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setNewCompetition({
        title: '',
        description: '',
        contractorInfo: '',
        imageUrl: '',
        videoUrl: '',
      });
      fetchCompetitions();
    } catch (error) {
      console.error('Error adding competition:', error);
    }
  };

  const handleUpdateCompetition = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      await axios.put(`/api/v1/competitions/admin/${editingCompetition.id}`, editingCompetition, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setEditingCompetition(null);
      fetchCompetitions();
    } catch (error) {
      console.error('Error updating competition:', error);
    }
  };

  const handleDeleteCompetition = async (id) => {
    if (!window.confirm('Are you sure you want to delete this competition?')) return;
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`/api/v1/competitions/admin/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchCompetitions();
    } catch (error) {
      console.error('Error deleting competition:', error);
    }
  };

  const competitionData = editingCompetition || newCompetition;
  const competitionChangeHandler = editingCompetition ? handleEditInputChange : handleInputChange;

  return (
    <div className="glowing-border text-white">
      <h1 className="text-2xl font-bold mb-4">Admin Competitions</h1>

      <form onSubmit={editingCompetition ? handleUpdateCompetition : handleAddCompetition} className="mb-8 p-4 bg-gray-800 rounded-lg">
        <h2 className="text-xl font-semibold mb-4">{editingCompetition ? 'Edit Competition' : 'Add New Competition'}</h2>
        <FormInput label="Title" name="title" value={competitionData.title} onChange={competitionChangeHandler} />
        <FormInput label="Description" name="description" value={competitionData.description} onChange={competitionChangeHandler} type="textarea" />
        <FormInput label="Contractor Info" name="contractorInfo" value={competitionData.contractorInfo} onChange={competitionChangeHandler} />
        <FormInput label="Image URL" name="imageUrl" value={competitionData.imageUrl} onChange={competitionChangeHandler} />
        <FormInput label="Video URL" name="videoUrl" value={competitionData.videoUrl} onChange={competitionChangeHandler} />
        
        <button type="submit" className="button">
          {editingCompetition ? 'Update Competition' : 'Add Competition'}
        </button>
        {editingCompetition && (
          <button type="button" onClick={() => setEditingCompetition(null)} className="button bg-gray-600 hover:bg-gray-700 ml-2">
            Cancel
          </button>
        )}
      </form>

      <h2 className="text-xl font-semibold mb-4">Existing Competitions</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {competitions.map((competition) => (
          <div key={competition.id} className="bg-gray-800 rounded-lg shadow-md p-4 flex flex-col">
            <div className="flex-grow">
              <h3 className="font-bold text-lg">{competition.title}</h3>
              <p className="text-gray-400 mb-2">{competition.description}</p>
              <p className="text-sm text-gray-500">Contractor: {competition.contractorInfo}</p>
              {competition.imageUrl && <img src={competition.imageUrl} alt={competition.title} className="w-full h-32 object-cover my-2 rounded" />}
              {competition.videoUrl && <p className="text-sm text-blue-400">Video: {competition.videoUrl}</p>}
            </div>
            <div className="mt-4 self-end">
              <button onClick={() => setEditingCompetition({ ...competition })} className="button bg-yellow-500 hover:bg-yellow-600">Edit</button>
              <button onClick={() => handleDeleteCompetition(competition.id)} className="button bg-red-600 hover:bg-red-800 ml-2">Delete</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AdminCompetitions;
