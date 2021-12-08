package ru.netology.cloudservicediplom.service.implement;


import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.netology.cloudservicediplom.dto.AuthDto;
import ru.netology.cloudservicediplom.model.TokenBlacklist;
import ru.netology.cloudservicediplom.repository.TokenBlacklistRepository;
import ru.netology.cloudservicediplom.security.JwtProvider;
import ru.netology.cloudservicediplom.service.AuthService;
import ru.netology.cloudservicediplom.service.UserService;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public String getToken(AuthDto authDto) {
        try {
            var login = authDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, authDto.getPassword()));
            var user = userService.findByLogin(login);
            return jwtProvider.createToken(login, user.getRoles());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неправильное имя пользователя или пароль ");
        }
    }

    @Override
    public void removeToken(String token) {
        var forbiddenToken = new TokenBlacklist();
        forbiddenToken.setToken(token);
        tokenBlacklistRepository.save(forbiddenToken);
    }
}
