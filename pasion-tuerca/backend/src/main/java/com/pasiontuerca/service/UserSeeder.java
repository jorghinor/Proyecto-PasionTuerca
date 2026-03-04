package com.pasiontuerca.service;

import com.pasiontuerca.model.AppUser;
import com.pasiontuerca.repository.AppUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class UserSeeder {
    private final AppUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserSeeder(AppUserRepository repo, PasswordEncoder passwordEncoder){ this.repo = repo; this.passwordEncoder = passwordEncoder; }

    @PostConstruct
    public void seed(){
        if(repo.findByUsername("admin").isEmpty()){
            AppUser a = new AppUser();
            a.setUsername("admin");
            a.setPassword(passwordEncoder.encode("admin123"));
            a.setEmail("admin@example.com");
            a.setRoles("ROLE_ADMIN,ROLE_EDITOR");
            repo.save(a);
        }
        if(repo.findByUsername("user").isEmpty()){
            AppUser u = new AppUser();
            u.setUsername("user");
            u.setPassword(passwordEncoder.encode("user123"));
            u.setEmail("user@example.com");
            u.setRoles("ROLE_USER");
            repo.save(u);
        }
    }
}
