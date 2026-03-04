package com.pasiontuerca.service;

import com.pasiontuerca.model.Biography;
import com.pasiontuerca.model.BiographyComment;
import com.pasiontuerca.repository.BiographyCommentRepository;
import com.pasiontuerca.repository.BiographyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BiographyCommentService {

    private final BiographyCommentRepository biographyCommentRepository;
    private final BiographyRepository biographyRepository;

    public BiographyCommentService(BiographyCommentRepository biographyCommentRepository, BiographyRepository biographyRepository) {
        this.biographyCommentRepository = biographyCommentRepository;
        this.biographyRepository = biographyRepository;
    }

    public List<BiographyComment> getCommentsByBiographyId(UUID biographyId) {
        return biographyCommentRepository.findByBiographyIdOrderByTimestampAsc(biographyId);
    }

    @Transactional
    public BiographyComment addComment(UUID biographyId, BiographyComment comment) {
        Biography biography = biographyRepository.findById(biographyId)
                .orElseThrow(() -> new RuntimeException("Biography not found with id: " + biographyId));

        comment.setBiography(biography);
        comment.setTimestamp(LocalDateTime.now());

        return biographyCommentRepository.save(comment);
    }
}
