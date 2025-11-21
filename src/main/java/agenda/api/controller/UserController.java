package agenda.api.controller;

import agenda.api.dto.LoginDTO;
import agenda.api.dto.TokenDTO;
import agenda.api.dto.UserDTO;
import agenda.api.entities.User;
import agenda.api.service.JWTokenService;
import agenda.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dadosLogin) {
        var dto = new UsernamePasswordAuthenticationToken(dadosLogin.username(), dadosLogin.password());
        var authentication = manager.authenticate(dto);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid LoginDTO dadosLogin) {
        var usuario=service.registerUser(dadosLogin);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        var user = service.deleteUser(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/listAll")
    public List<UserDTO> listAllUsers() {
        return service.listAllUsers();
    }
}