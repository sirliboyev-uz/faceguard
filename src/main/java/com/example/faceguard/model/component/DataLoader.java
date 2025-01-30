package com.example.faceguard.model.component;
import com.example.faceguard.model.Role;
import com.example.faceguard.model.Users;
import com.example.faceguard.model.permission.Permission;
import com.example.faceguard.model.utils.Constanta;
import com.example.faceguard.repository.RoleRepository;
import com.example.faceguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.faceguard.model.permission.Permission.READ_ROLE;
import static com.example.faceguard.model.permission.Permission.READ_USER;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Value(value = "${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("Successfully runner");
        if (initMode.equals("always")){
            Permission[] permissions=Permission.values();
            Role adminRole = roleRepository.save(new Role(
                    Constanta.ADMIN,
                    Arrays.asList(permissions)
            ));
            Role userRole = roleRepository.save(new Role(
                    Constanta.USER,
                    Arrays.asList(READ_ROLE, READ_USER)
            ));
            userRepository.save(new Users(
                    "Admin", "Admin", null, "admin@gmail.com", "+998945744373", null, null, null, "admin", "admin", null, adminRole, null));
            userRepository.save(new Users(
                    "Users", "user", null, "user@gmail.com", "+998995581848", null, null, null, "user", "user", null, userRole, null));
        }
    }
}
