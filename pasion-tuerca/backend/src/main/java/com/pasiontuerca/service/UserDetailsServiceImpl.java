package com.pasiontuerca.service;

import com.pasiontuerca.model.AppUser;
import com.pasiontuerca.repository.AppUserRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository userRepository;

    public UserDetailsServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl: Loading user by username: " + username);
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        System.out.println("UserDetailsServiceImpl: Found user: " + appUser.getUsername() + " with roles: " + appUser.getRoles());

        return new User(appUser.getUsername(), appUser.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(appUser.getRoles()));
    }
}
