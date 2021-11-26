package service;

import dto.AuthDto;

public interface AuthService {

    String getToken(AuthDto authDto);

    void removeToken(String token);
}
