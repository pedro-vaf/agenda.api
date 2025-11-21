package agenda.api.dto;

import agenda.api.entities.Contact;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ContactDTO(
    /* Id útil para requisições GET, PUT e DELETE */
        Long Id,

        @NotBlank(message = "The name is required!")
        @Size(min = 2, max = 50, message = "The name must be between 2 and 50 characters long!")
        String name,

        @NotBlank(message = "The lastname is required!")
        @Size(min = 2, max = 150, message = "The name must be between 2 and 150 characters long!")
        String lastname,

        @Email(message = "The email address must be valid!")
        String email,

        @Valid
        List<TelephoneDTO> telephones
) {
    public ContactDTO(Contact contact){this(contact.getId(), contact.getName(), contact.getLastName(), contact.getEmail(), contact.getTelephone().stream().map(TelephoneDTO::new).toList());}
}