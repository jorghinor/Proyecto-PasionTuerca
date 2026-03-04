package com.pasiontuerca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "refresh_token")
@Data
public class RefreshToken {
    @Id
    @GeneratedValue
    private UUID id;
    private String token;
    private OffsetDateTime expiry;
    @ManyToOne
    private AppUser user;
}
