package agenda.api.controller;

import agenda.api.dto.LoginDTO;
import agenda.api.dto.TokenDTO;
import agenda.api.entities.User;
import agenda.api.service.JWTokenService;
import agenda.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private AuthenticationManager manager;
    private UserService service;
    private JWTokenService tokenService;

    public UserController(AuthenticationManager manager, UserService service, JWTokenService tokenService) {
        this.manager = manager;
        this.service = service;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO LoginData){
        var dto = new UsernamePasswordAuthenticationToken(LoginData.login(), LoginData.password());
        var authentication = manager.authenticate(dto);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO())
    }
}
