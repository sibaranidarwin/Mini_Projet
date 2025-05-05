package com.example.mini_project.security;

import com.example.mini_project.repository.UserRepository;
import com.example.mini_project.util.CustomerUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByUsername(username)
                    .map(CustomerUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        } catch (Exception e) {
            System.err.println("[ERROR] Exception in loadUserByUsername: " + e.getMessage());
            e.printStackTrace(); // log full stacktrace
            throw e; // rethrow biar tetap dilempar ke Spring Security
        }
    }

}
