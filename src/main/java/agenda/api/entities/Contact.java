package agenda.api.entities;

import agenda.api.dto.ContactDTO;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String lastName;
    private String password;
    private String email;
    @Column(name = "date_of_creation", nullable = false)
    private LocalDateTime dateOfCreation;

    /* Relacionamento: Muitos Contatos pertencem a um único Usuario */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; /* Chave estrangeira para a tabela usuarios */

    /* Relacionamento: Um Contato pode ter muitos Telefones */
    /* orphanRemoval = true garante que se um Telefone for desassociado do Contato, ele será deletado */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telephone> telephone;

    public Contact() {
        super();
    }

    public Contact(ContactDTO contactDTO, UserDetails userDetails){
        this.name = contactDTO.name();
        this.lastName = contactDTO.lastname();
        this.email = contactDTO.email();
        this.user = (User)userDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Telephone> getTelephone() {
        return telephone;
    }

    public void setTelephone(List<Telephone> telephone) {
        this.telephone = telephone;
    }
}