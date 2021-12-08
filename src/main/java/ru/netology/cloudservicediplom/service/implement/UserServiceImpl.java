package ru.netology.cloudservicediplom.service.implement;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.cloudservicediplom.exception.CloudServiceDiplomNotFoundException;
import ru.netology.cloudservicediplom.model.User;
import ru.netology.cloudservicediplom.repository.UserRepository;
import ru.netology.cloudservicediplom.service.UserService;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByLogin(String login) {
        var user = userRepository.findByUsername(login).orElseThrow(()
                -> new CloudServiceDiplomNotFoundException(format("Пользователь с логином=[%s] не найден", login)));
        return user;
    }

    @Override
    public User findById(Long id) {
        var user = userRepository.findById(id).orElseThrow(()
                -> new CloudServiceDiplomNotFoundException(format("Пользователь с id=[%s] не найден", id)));
        return user;
    }

}
