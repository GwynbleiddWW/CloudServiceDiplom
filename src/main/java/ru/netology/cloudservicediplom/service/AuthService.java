package ru.netology.cloudservicediplom.service;

import ru.netology.cloudservicediplom.dto.AuthDto;

public interface AuthService {

    String getToken(AuthDto authDto);

    void removeToken(String token);
}
