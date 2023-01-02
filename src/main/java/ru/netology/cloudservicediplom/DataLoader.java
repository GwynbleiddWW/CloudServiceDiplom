package ru.netology.cloudservicediplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.netology.cloudservicediplom.model.Role;
import ru.netology.cloudservicediplom.model.User;
import ru.netology.cloudservicediplom.model.UserCondition;
import ru.netology.cloudservicediplom.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    List<Role> roleList = new ArrayList<>();
    @Override
    public void run(String... args) {
        userRepository.save(new User("lala", "lala", "lala", "lala", passwordEncoder.encode("lala"), roleList, UserCondition.ACTIVE));
    }

}
