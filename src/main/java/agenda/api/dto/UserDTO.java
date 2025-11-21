package agenda.api.dto;

import agenda.api.entities.User;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        Long id,

        @NotBlank(message = "The name is required!")
        String username) {
    public UserDTO(User user){
        this(user.getId(), user.getUsername());
    }
}