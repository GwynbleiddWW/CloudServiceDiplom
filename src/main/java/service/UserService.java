package service;

import model.User;

public interface UserService {

    User findByLogin(String login);

    User findById(Long id);
}
