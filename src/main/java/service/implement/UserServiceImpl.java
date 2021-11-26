package service.implement;

import exception.CloudServiceDiplomNotFoundException;
import lombok.AllArgsConstructor;
import model.User;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import service.UserService;

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
