import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useSwipeable } from 'react-swipeable';

const Carousel = () => {
  const [carouselItems, setCarouselItems] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const fetchCarouselItems = async () => {
      try {
        const response = await axios.get('/api/v1/carousel');
        setCarouselItems(response.data);
      } catch (error) {
        console.error('Error fetching carousel items:', error);
      }
    };
    fetchCarouselItems();
  }, []);

  useEffect(() => {
    if (carouselItems.length > 0) {
      const interval = setInterval(() => {
        setCurrentIndex((prevIndex) => (prevIndex + 1) % carouselItems.length);
      }, 3000); // 3 seconds
      return () => clearInterval(interval);
    }
  }, [carouselItems]);

  const handleSwipeLeft = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % carouselItems.length);
  };

  const handleSwipeRight = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1 + carouselItems.length) % carouselItems.length);
  };

  const handlers = useSwipeable({
    onSwipedLeft: handleSwipeLeft,
    onSwipedRight: handleSwipeRight,
    preventScrollOnSwipe: true,
    trackMouse: true
  });

  const handleImageClick = (whatsappLink) => {
    if (whatsappLink) {
      window.open(whatsappLink, '_system'); // '_system' for Capacitor/Cordova to open external browser
    }
  };

  if (carouselItems.length === 0) {
    return <div className="text-center py-4">No carousel items available.</div>;
  }

  const currentItem = carouselItems[currentIndex];

  return (
    <div {...handlers} className="relative w-full overflow-hidden" style={{ height: '300px' }}>
      <div
        className="flex transition-transform duration-500 ease-in-out"
        style={{ transform: `translateX(-${currentIndex * 100}%)` }}
      >
        {carouselItems.map((item, index) => (
          <div key={item.id} className="w-full flex-shrink-0" onClick={() => handleImageClick(item.whatsappLink)}>
            <img
              src={item.imageUrl}
              alt={`Carousel item ${index + 1}`}
              className="w-full h-full object-cover cursor-pointer"
            />
          </div>
        ))}
      </div>
      <div className="absolute bottom-2 left-0 right-0 flex justify-center space-x-2">
        {carouselItems.map((_, index) => (
          <span
            key={index}
            className={`block w-2 h-2 rounded-full ${
              currentIndex === index ? 'bg-white' : 'bg-gray-500'
            }`}
          ></span>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
