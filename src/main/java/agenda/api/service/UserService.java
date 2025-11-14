package agenda.api.service;

import agenda.api.dto.LoginDTO;
import agenda.api.dto.UserDTO;
import agenda.api.entities.User;
import agenda.api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO registerUser(LoginDTO loginDTO){
        var user = new User(loginDTO);
        /* Encrypt to Bcrypt the password before saving */
        user.setPassword(passwordEncoder.encode(loginDTO.password()));
        userRepository.save(user);
        return new UserDTO(user);
    }

    public UserDTO deleteUser(Long id){
        var user = userRepository.getReferenceById(id);
        userRepository.delete(user);
        return new UserDTO(user);
    }

    public List<UserDTO> listAllUsers(){
        return userRepository.findAll().stream().map(UserDTO::new).toList();
    }
}