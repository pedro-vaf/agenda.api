package agenda.api.controller;

import agenda.api.dto.ContactDTO;
import agenda.api.dto.ListTelephoneDTO;
import agenda.api.dto.TelephoneDTO;
import agenda.api.service.ContactService;
import agenda.api.service.TelephoneService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final TelephoneService telephoneService;

    public ContactController(ContactService contactService, TelephoneService telephoneService) {
        this.contactService = contactService;
        this.telephoneService = telephoneService;
    }

    @PostMapping
    public ResponseEntity<List<ContactDTO>> registerContact(@RequestBody @Valid ContactDTO contactDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ContactDTO newContactDTO = contactService.registerContact(contactDTO, username);
        /*  Retorna a resposta com o status 201 Created e o corpo contendo o novo contato */
        return ResponseEntity.status(201).body(Collections.singletonList(newContactDTO));
    }

    @GetMapping
    public ResponseEntity<List<ContactDTO>> listContact(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ContactDTO> contacts = contactService.listContactUser(username);
        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id){
        ContactDTO contact = contactService.getContactId(id);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContactById(@PathVariable Long id, @RequestBody @Valid ContactDTO contactDTO){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ContactDTO contact = contactService.updateContactId(id, contactDTO, username);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContactDTO> deleteContactById(@PathVariable Long id){
        ContactDTO contact = contactService.deleteContactId(id);
        return ResponseEntity.ok(contact);
    }

    /* Add lista de um ou mais telefones a um contato existente */
    @PostMapping("/{contactId}/telephones")
    public ResponseEntity<ContactDTO> addTelephoneContact(@PathVariable Long contactId, @RequestBody ListTelephoneDTO listTelephoneDTO){
        ContactDTO contact = telephoneService.registerTelephone(listTelephoneDTO, contactId);
        return ResponseEntity.ok(contact);
    }

    /* atualizar um telefone de um contato existente */
    @PutMapping("/{contactId}/telephones/{telephoneId}")
    public ResponseEntity<ContactDTO> updateTelephoneContact(@PathVariable Long contactId, @PathVariable Long telephoneId, @RequestBody @Valid TelephoneDTO telephoneDTO, ListTelephoneDTO listTelephoneDTO){
        ContactDTO contact = telephoneService.updateTelephone(listTelephoneDTO, contactId, telephoneId, telephoneDTO);
        return ResponseEntity.ok(contact);
    }

    /* apagar um telefone de um contato existente */
    @DeleteMapping("/{contactId}/telephones/{telephoneId}")
    public ResponseEntity<ContactDTO> deleteTelephoneContact(@PathVariable Long contactId, @PathVariable Long telephoneId, @RequestBody @Valid ListTelephoneDTO listTelephoneDTO){
        ContactDTO contact = telephoneService.deleteTelephone(listTelephoneDTO, contactId, telephoneId);
        return ResponseEntity.ok(contact);
    }
}