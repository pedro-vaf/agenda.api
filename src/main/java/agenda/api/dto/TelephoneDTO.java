package agenda.api.dto;

import agenda.api.entities.CategoryTelephone;
import agenda.api.entities.Telephone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TelephoneDTO(
        /* Id útil para requisições GET, PUT e DELETE */
        Long id,

        @NotBlank(message = "The phone number is required!")
        String number,

        @NotNull(message = "The phone number category is required!")
        /* A categoria (PESSOAL, PROFISSIONAL, etc.) será esperada como uma String */
        CategoryTelephone categoryTelephone,

        /* Indica se é o número principal do contato */
        boolean principal
) {
    public TelephoneDTO(Telephone telephone){ this(telephone.getId(), telephone.getNumber(), telephone.getCategoryTelephone(), telephone.getPrincipal()); }
}