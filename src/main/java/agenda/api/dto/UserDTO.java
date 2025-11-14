package agenda.api.dto;

import agenda.api.entities.User;

public record UserDTO(Long id, String username) {
    public UserDTO(User user){
        this(user.getId(), user.getUsername());
    }
}
