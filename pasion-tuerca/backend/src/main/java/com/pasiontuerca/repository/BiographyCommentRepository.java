package com.pasiontuerca.repository;

import com.pasiontuerca.model.BiographyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BiographyCommentRepository extends JpaRepository<BiographyComment, Long> {
    List<BiographyComment> findByBiographyIdOrderByTimestampAsc(UUID biographyId);
}