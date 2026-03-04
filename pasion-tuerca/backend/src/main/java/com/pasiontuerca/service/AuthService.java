package com.pasiontuerca.service;

import com.pasiontuerca.model.AppUser;
import com.pasiontuerca.model.RefreshToken;
import com.pasiontuerca.repository.AppUserRepository;
import com.pasiontuerca.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final AppUserRepository users;
    private final RefreshTokenRepository refreshRepo;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.refresh-expiration-days}")
    private int refreshDays;

    public AuthService(AppUserRepository users, RefreshTokenRepository refreshRepo, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.refreshRepo = refreshRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // For demo only: validate by username/password stored in DB seeded at startup
    public Optional<AppUser> validate(String username, String password){
        Optional<AppUser> u = users.findByUsername(username);
        if(u.isPresent() && passwordEncoder.matches(password, u.get().getPassword())) return u;
        return Optional.empty();
    }

    public RefreshToken createRefreshToken(AppUser user){
        RefreshToken t = new RefreshToken();
        t.setToken(UUID.randomUUID().toString());
        t.setUser(user);
        t.setExpiry(OffsetDateTime.now().plusDays(refreshDays));
        return refreshRepo.save(t);
    }

    public Optional<RefreshToken> findRefresh(String token){
        return refreshRepo.findByToken(token);
    }

    public void revokeUserTokens(UUID userId){
        refreshRepo.deleteByUserId(userId);
    }
}
