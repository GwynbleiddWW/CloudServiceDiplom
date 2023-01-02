package ru.netology.cloudservicediplom.restController;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.cloudservicediplom.dto.AuthDto;
import ru.netology.cloudservicediplom.dto.TokenDto;
import ru.netology.cloudservicediplom.service.AuthService;


@AllArgsConstructor
@RestController
@RequestMapping(value = "/")
public class UserController {

    private final AuthService authService;

    @PostMapping(path = "login")
    public TokenDto login(@RequestBody AuthDto authDto) {
        var token = new TokenDto();
        token.setValue(authService.getToken(authDto));
        return token;
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String token) {
        authService.removeToken(token);
        return new ResponseEntity(HttpStatus.OK);
    }

}
