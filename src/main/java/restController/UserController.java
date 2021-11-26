package restController;

import dto.AuthDto;
import dto.TokenDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AuthService;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/")
public class UserController {

    private final AuthService authService;

    @PostMapping("login")
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
