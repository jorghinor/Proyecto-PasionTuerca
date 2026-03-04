package com.pasiontuerca.service;

import com.pasiontuerca.model.Biography;
import com.pasiontuerca.model.BiographyComment;
import com.pasiontuerca.repository.BiographyCommentRepository;
import com.pasiontuerca.repository.BiographyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BiographyService {

    @Autowired
    private BiographyRepository biographyRepository;

    @Autowired
    private BiographyCommentRepository biographyCommentRepository;

    // Biography operations
    public List<Biography> getAllBiographies() {
        return biographyRepository.findAll();
    }

    public Optional<Biography> getBiographyById(UUID id) {
        return biographyRepository.findById(id);
    }

    public Biography createBiography(Biography biography) {
        return biographyRepository.save(biography);
    }

    public Biography updateBiography(UUID id, Biography updatedBiography) {
        return biographyRepository.findById(id).map(biography -> {
            biography.setContent(updatedBiography.getContent());
            biography.setImageUrls(updatedBiography.getImageUrls());
            biography.setVideoUrls(updatedBiography.getVideoUrls());
            return biographyRepository.save(biography);
        }).orElseThrow(() -> new RuntimeException("Biography not found with id " + id));
    }

    public void deleteBiography(UUID id) {
        biographyRepository.deleteById(id);
    }

    // Biography Comment operations
    public List<BiographyComment> getCommentsByBiographyId(UUID biographyId) {
        return biographyCommentRepository.findByBiographyIdOrderByTimestampAsc(biographyId);
    }

    public BiographyComment addCommentToBiography(UUID biographyId, BiographyComment comment) {
        return biographyRepository.findById(biographyId).map(biography -> {
            comment.setBiography(biography);
            comment.setTimestamp(LocalDateTime.now());
            return biographyCommentRepository.save(comment);
        }).orElseThrow(() -> new RuntimeException("Biography not found with id " + biographyId));
    }

    public void deleteComment(Long commentId) {
        biographyCommentRepository.deleteById(commentId);
    }

    public List<Biography> all() {
        return biographyRepository.findAll();
    }

    public Biography save(Biography b) {
        return biographyRepository.save(b);
    }
}