package com.example.api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("HR: " + encoder.encode("ok"));
        System.out.println("MANAGER: " + encoder.encode("manager123"));
    }
}
