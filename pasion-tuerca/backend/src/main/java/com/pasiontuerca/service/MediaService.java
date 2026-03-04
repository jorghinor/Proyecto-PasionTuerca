package com.pasiontuerca.service;

import com.pasiontuerca.model.Media;
import com.pasiontuerca.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MediaService {
    private final MediaRepository repo;

    public MediaService(MediaRepository repo) {
        this.repo = repo;
    }

    public Media save(Media m){ return repo.save(m); }
    public List<Media> activeCarousel(){ return repo.findByActiveTrueOrderByOrderIndexAsc(); }
    public List<Media> findAll() { return repo.findAll(); }
    public void delete(UUID id){ repo.deleteById(id); }
}
