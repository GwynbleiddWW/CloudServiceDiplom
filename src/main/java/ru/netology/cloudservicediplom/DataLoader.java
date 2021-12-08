package ru.netology.cloudservicediplom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.netology.cloudservicediplom.model.Role;
import ru.netology.cloudservicediplom.model.User;
import ru.netology.cloudservicediplom.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Component
public class DataLoader implements CommandLineRunner /*ApplicationRunner*/ {

    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    List<Role> roleList = new ArrayList<>();
   /* public void run(ApplicationArguments args) {
        userRepository.save(User.builder()
                .username("Man")
                .password("1234")
                .email("@mail")
                .build());
    }
   public void run(ApplicationArguments args) {
       userRepository.save(new User("jojo", "lala", "lala", "@mail", "1234", roleList));
   }*/

    //@PostConstruct - ещё вариант

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("lala", "lala", "lala", "lala", "lala", roleList));
    }


}
