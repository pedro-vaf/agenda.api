package agenda.api.service;

import agenda.api.dto.ContactDTO;
import agenda.api.entities.Contact;
import agenda.api.entities.Telephone;
import agenda.api.entities.User;
import agenda.api.repositories.ContactRepository;
import agenda.api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;
    private UserRepository userRepository;

    public ContactService(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    public ContactDTO registerContact(ContactDTO contactDTO, String username){
        var user = userRepository.findByUsername(username);
        Contact newContact = new Contact(contactDTO, user);
        if (contactDTO.telephones() != null){
            contactDTO.telephones().forEach(t -> {
                newContact.getTelephone().add(new Telephone(t, newContact));
            });
        }
        contactRepository.save(newContact);
        return new ContactDTO(newContact);
    }

    public List<ContactDTO> listContactUser(String username){
        var user = (User)userRepository.findByUsername(username);
        var contacts = user.getContact();
        return contacts.stream().map(ContactDTO::new).toList();
    }

    public ContactDTO listContactId(Long id){
        var contact = contactRepository.getReferenceById(id);
        return new ContactDTO(contact);
    }

    public ContactDTO updateContactId(Long id, ContactDTO contactDTO, String username){
        var user = (User)userRepository.findByUsername(username);
        var contact = contactRepository.getReferenceById(id);
        if (!contact.getUser().getId().equals(user.getId())){
            throw new RuntimeException("The contact does not belong to the authenticated user!");
        }
        contact.setName(contactDTO.name());
        contact.setLastName(contactDTO.lastname());
        contact.setEmail(contactDTO.email());
        contactRepository.save(contact);
        return new ContactDTO(contact);
    }

    public ContactDTO deleteContactId(Long id){
        var contact = contactRepository.getReferenceById(id);
        contactRepository.delete(contact);
        return new ContactDTO(contact);
    }
}