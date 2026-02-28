package com.pejjok.blog.config;

import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserService userService;

    @Value("${app.admin.name}")
    public String name;
    @Value("${app.admin.email}")
    public String email;
    @Value("${app.admin.password}")
    public String password;

    public AdminInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.existsByEmail(email)){
            return;
        }

        UserEntity admin = UserEntity.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
        userService.createUser(admin);

        System.out.println("═══════════════════════════════════════════════");
        System.out.println("Default admin created:");
        System.out.println("  email:    " + email);
        System.out.println("  password: " + password);
        System.out.println("═══════════════════════════════════════════════");
    }
}
