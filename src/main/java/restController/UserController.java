package restController;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import service.AuthService;

@AllArgsConstructor
@RestController
public class UserController {

    private final AuthService authService;

    @PostMapping("login")
    public void login(){
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(){
        return null;
    }

}
