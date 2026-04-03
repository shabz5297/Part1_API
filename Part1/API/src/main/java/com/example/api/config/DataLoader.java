package com.example.api.config;

import com.example.api.entity.AppUser;
import com.example.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {
// creates two users on run automatically, rather than manually using SQL
    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                AppUser hr = new AppUser();
                hr.setUsername("hr");
                hr.setPassword(passwordEncoder.encode("hr123"));
                hr.setRole("HR");
                userRepository.save(hr);

                AppUser manager = new AppUser();
                manager.setUsername("manager");
                manager.setPassword(passwordEncoder.encode("manager123"));
                manager.setRole("MANAGER");
                userRepository.save(manager);
            }
        };
    }
}