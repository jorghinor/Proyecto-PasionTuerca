package com.pasiontuerca.repository;
import com.pasiontuerca.model.Biography;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BiographyRepository extends JpaRepository<Biography, UUID> {
}
