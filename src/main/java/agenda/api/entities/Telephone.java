package agenda.api.entities;

import agenda.api.dto.TelephoneDTO;
import jakarta.persistence.*;

@Entity(name = "telephone")
public class Telephone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String number;
    /* Mapeia o Enum para o banco de dados como uma String */
    @Enumerated(EnumType.STRING)
    @Column(name = "category_telephone", nullable = false)
    private CategoryTelephone categoryTelephone;
    @Column(name = "is_principal", nullable = false)
    private Boolean principal;

    /* Relacionamento: Muitos Telefones pertencem a um único Contato */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact; // Chave estrangeira para a tabela contatos

    public Telephone() {
        super();
    }

    public Telephone (TelephoneDTO telephoneDTO, Contact contact){
        this.number = telephoneDTO.number();
        this.categoryTelephone = telephoneDTO.categoryTelephone();
        this.principal = telephoneDTO.principal();
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CategoryTelephone getCategoryTelephone() {
        return categoryTelephone;
    }

    public void setCategoryTelephone(CategoryTelephone categoryTelephone) {
        this.categoryTelephone = categoryTelephone;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}