package ru.netology.cloudservicediplom.service;

import ru.netology.cloudservicediplom.model.User;

public interface UserService {

    User findByLogin(String login);

    User findById(Long id);
}
