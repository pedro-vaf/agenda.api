package agenda.api.service;

import agenda.api.dto.ContactDTO;
import agenda.api.dto.ListTelephoneDTO;
import agenda.api.dto.TelephoneDTO;
import agenda.api.entities.Telephone;
import agenda.api.repositories.ContactRepository;
import agenda.api.repositories.TelephoneRepository;
import org.springframework.stereotype.Service;

@Service
public class TelephoneService {

    private final TelephoneRepository telephoneRepository;
    private final ContactRepository contactRepository;

    public TelephoneService(TelephoneRepository telephoneRepository, ContactRepository contactRepository) {
        this.telephoneRepository = telephoneRepository;
        this.contactRepository = contactRepository;
    }

    public ContactDTO registerTelephone(ListTelephoneDTO listTelephoneDTO, Long contactId){
        var contact = this.contactRepository.getReferenceById(contactId);
        if (!listTelephoneDTO.numbers().isEmpty()){
            this.telephoneRepository.saveAll(listTelephoneDTO.numbers().stream().map(telephoneDTO -> new Telephone(telephoneDTO, contact)).toList());
        }
        return new ContactDTO(contact);
    }

    public ContactDTO deleteTelephone(ListTelephoneDTO listTelephoneDTO, Long contactId, Long telephoneId){
        if (!listTelephoneDTO.numbers().isEmpty()){
            var telephone = this.telephoneRepository.getReferenceById(telephoneId);
            if (telephone.getContact().getId().equals(contactId)){
                this.telephoneRepository.delete(telephone);
            } else {
                throw new RuntimeException("The phone number does not belong to the contact!");
            }
        } else {
            throw new RuntimeException("No phone list for this contact!");
        }
        var contact = contactRepository.getReferenceById(contactId);
        return  new ContactDTO(contact);
    }

    public ContactDTO updateTelephone(ListTelephoneDTO listTelephoneDTO, Long contactId, Long telephoneId, TelephoneDTO newNumber){
        if (!listTelephoneDTO.numbers().isEmpty()){
            var telephone = this.telephoneRepository.getReferenceById(telephoneId);
            if (telephone.getContact().getId().equals(contactId)){
                telephone.setNumber(newNumber.number());
                telephone.setCategoryTelephone(newNumber.categoryTelephone());
                telephone.setPrincipal(newNumber.principal());
                this.telephoneRepository.save(telephone);
            } else {
                throw new RuntimeException("The phone number does not belong to the contact!");
            }
        } else {
            throw new RuntimeException("No phone list for this contact!");
        }
        var contact = this.contactRepository.getReferenceById(contactId);
        return  new ContactDTO(contact);
    }
}