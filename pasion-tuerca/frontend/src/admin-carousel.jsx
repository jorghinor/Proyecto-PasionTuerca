import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AdminCarousel = () => {
  const [carouselItems, setCarouselItems] = useState([]);
  const [newItem, setNewItem] = useState({ imageUrl: '', whatsappLink: '', itemOrder: 0, active: true });
  const [editingItem, setEditingItem] = useState(null);

  useEffect(() => {
    fetchCarouselItems();
  }, []);

  const fetchCarouselItems = async () => {
    try {
      const token = localStorage.getItem('token');
      const response = await axios.get('/api/v1/carousel/admin', {
        headers: { Authorization: `Bearer ${token}` }
      });
      setCarouselItems(response.data);
    } catch (error) {
      console.error('Error fetching carousel items:', error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setNewItem((prev) => ({ ...prev, [name]: type === 'checkbox' ? checked : value }));
  };

  const handleEditInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setEditingItem((prev) => ({ ...prev, [name]: type === 'checkbox' ? checked : value }));
  };

  const handleAddItem = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      await axios.post('/api/v1/carousel/admin', newItem, { headers: { Authorization: `Bearer ${token}` } });
      setNewItem({ imageUrl: '', whatsappLink: '', itemOrder: 0, active: true });
      fetchCarouselItems();
    } catch (error) {
      console.error('Error adding carousel item:', error);
    }
  };

  const handleUpdateItem = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem('token');
      await axios.put(`/api/v1/carousel/admin/${editingItem.id}`, editingItem, { headers: { Authorization: `Bearer ${token}` } });
      setEditingItem(null);
      fetchCarouselItems();
    } catch (error) {
      console.error('Error updating carousel item:', error);
    }
  };

  const handleDeleteItem = async (id) => {
    if (!window.confirm('Are you sure you want to delete this item?')) return;
    try {
      const token = localStorage.getItem('token');
      await axios.delete(`/api/v1/carousel/admin/${id}`, { headers: { Authorization: `Bearer ${token}` } });
      fetchCarouselItems();
    } catch (error) {
      console.error('Error deleting carousel item:', error);
    }
  };

  const itemData = editingItem || newItem;
  const itemChangeHandler = editingItem ? handleEditInputChange : handleInputChange;

  return (
    <div className="glowing-border text-white">
      <h1 className="text-2xl font-bold mb-4">Admin Carousel</h1>

      <form onSubmit={editingItem ? handleUpdateItem : handleAddItem} className="mb-8 p-4 bg-gray-800 rounded-lg">
        <h2 className="text-xl font-semibold mb-4">{editingItem ? 'Edit Item' : 'Add New Item'}</h2>
                <div className="mb-4">
          <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="imageUrl">Image URL:</label>
          <input
            type="text"
            id="imageUrl"
            name="imageUrl"
            value={itemData.imageUrl}
            onChange={itemChangeHandler}
            className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="whatsappLink">WhatsApp Link:</label>
          <input
            type="text"
            id="whatsappLink"
            name="whatsappLink"
            value={itemData.whatsappLink}
            onChange={itemChangeHandler}
            className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-300 text-sm font-bold mb-2" htmlFor="itemOrder">Order:</label>
          <input
            type="number"
            id="itemOrder"
            name="itemOrder"
            value={itemData.itemOrder}
            onChange={itemChangeHandler}
            className="shadow appearance-none border rounded w-full py-2 px-3 bg-gray-700 text-white leading-tight focus:outline-none focus:shadow-outline"
            required
          />
        </div>
        <div className="mb-4 flex items-center">
          <input
            type="checkbox"
            id="active"
            name="active"
            checked={itemData.active}
            onChange={itemChangeHandler}
            className="mr-2 leading-tight"
          />
          <label className="text-gray-300 text-sm font-bold" htmlFor="active">Active</label>
        </div>
        <button type="submit" className="button">
          {editingItem ? 'Update Item' : 'Add Item'}
        </button>
        {editingItem && (
          <button type="button" onClick={() => setEditingItem(null)} className="button bg-gray-600 hover:bg-gray-700 ml-2">
            Cancel
          </button>
        )}
      </form>

      <h2 className="text-xl font-semibold mb-4">Existing Items</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {carouselItems.map((item) => (
          <div key={item.id} className="bg-gray-800 rounded-lg shadow-md p-4">
            <img src={item.imageUrl} alt="Carousel Item" className="w-full h-32 object-cover mb-2 rounded" />
            <p><strong>Order:</strong> {item.itemOrder}</p>
            <p><strong>Active:</strong> {item.active ? 'Yes' : 'No'}</p>
            <p><strong>WhatsApp:</strong> <a href={item.whatsappLink} target="_blank" rel="noopener noreferrer" className="text-cyan-400 hover:underline">Link</a></p>
            <div className="mt-4">
              <button onClick={() => setEditingItem({ ...item })} className="button bg-yellow-500 hover:bg-yellow-600">Edit</button>
              <button onClick={() => handleDeleteItem(item.id)} className="button bg-red-600 hover:bg-red-800 ml-2">Delete</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AdminCarousel;
