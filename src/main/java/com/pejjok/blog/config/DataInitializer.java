package com.pejjok.blog.config;

import com.pejjok.blog.domain.UserRole;
import com.pejjok.blog.domain.entities.RoleEntity;
import com.pejjok.blog.domain.entities.UserEntity;
import com.pejjok.blog.repositories.RoleReposiory;
import com.pejjok.blog.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final RoleReposiory roleReposiory;

    @Value("${app.admin.name}")
    public String name;
    @Value("${app.admin.email}")
    public String email;
    @Value("${app.admin.password}")
    public String password;

    public DataInitializer(UserService userService, RoleReposiory roleReposiory) {
        this.userService = userService;
        this.roleReposiory = roleReposiory;
    }

    @Override
    public void run(String... args) throws Exception {
        rolesInit();
        adminInit();
    }

    private void rolesInit(){
        if(!roleReposiory.existsByName(UserRole.ADMIN.getRole())){
            RoleEntity role_admin = RoleEntity.builder().name("ROLE_ADMIN").build();
            roleReposiory.save(role_admin);
        }
        if(!roleReposiory.existsByName(UserRole.EDITOR.getRole())){
            RoleEntity role_editor = RoleEntity.builder().name(UserRole.EDITOR.getRole()).build();
            roleReposiory.save(role_editor);
        }
        if(!roleReposiory.existsByName(UserRole.USER.getRole())){
            RoleEntity role_user = RoleEntity.builder().name(UserRole.USER.getRole()).build();
            roleReposiory.save(role_user);
        }
    }

    private void adminInit() {
        if (userService.existsByEmail(email)){
            return;
        }

        UserEntity admin = UserEntity.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(roleReposiory.findByName(UserRole.ADMIN.getRole()))
                .build();
        userService.createUser(admin);

        System.out.println("═══════════════════════════════════════════════");
        System.out.println("Default admin created:");
        System.out.println("  email:    " + email);
        System.out.println("  password: " + password);
        System.out.println("═══════════════════════════════════════════════");
    }
}
