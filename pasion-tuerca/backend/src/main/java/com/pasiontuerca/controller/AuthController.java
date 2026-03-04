package com.pasiontuerca.controller;

import com.pasiontuerca.model.AppUser;
import com.pasiontuerca.model.RefreshToken;
import com.pasiontuerca.security.JwtUtil;
import com.pasiontuerca.service.AuthService;
import com.pasiontuerca.service.UserSeeder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService auth;
    private final JwtUtil jwtUtil;
    private final UserSeeder seeder;

    public AuthController(AuthService auth, JwtUtil jwtUtil, UserSeeder seeder){
        this.auth = auth; this.jwtUtil = jwtUtil; this.seeder = seeder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req){
        Optional<AppUser> u = auth.validate(req.getUsername(), req.getPassword());
        if(u.isPresent()){
            String access = jwtUtil.generateToken(u.get().getUsername());
            RefreshToken rt = auth.createRefreshToken(u.get());
            return ResponseEntity.ok(Map.of("accessToken", access, "refreshToken", rt.getToken()));
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String,String> body){
        String token = body.get("refreshToken");
        Optional<RefreshToken> t = auth.findRefresh(token);
        if(t.isEmpty()) return ResponseEntity.status(401).body("Invalid refresh token");
        if(t.get().getExpiry().isBefore(java.time.OffsetDateTime.now())) return ResponseEntity.status(401).body("Expired");
        String username = t.get().getUser().getUsername();
        String newAccess = jwtUtil.generateToken(username);
        // rotate refresh token
        auth.revokeUserTokens(t.get().getUser().getId());
        RefreshToken newRt = auth.createRefreshToken(t.get().getUser());
        return ResponseEntity.ok(Map.of("accessToken", newAccess, "refreshToken", newRt.getToken()));
    }

    // For demo: endpoint to seed default users
    @PostMapping("/seed")
    public ResponseEntity<?> seed(){
        seeder.seed();
        return ResponseEntity.ok("seeded");
    }

    @Data
    static class AuthRequest {
        private String username;
        private String password;
    }
}
