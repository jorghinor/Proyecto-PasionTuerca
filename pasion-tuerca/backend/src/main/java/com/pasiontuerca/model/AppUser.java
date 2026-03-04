package com.pasiontuerca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Data
public class AppUser {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String roles; // comma separated, e.g. ROLE_ADMIN,ROLE_EDITOR
    private boolean enabled = true;
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
