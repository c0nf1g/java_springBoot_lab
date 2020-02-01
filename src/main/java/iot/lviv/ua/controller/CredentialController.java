package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.CredentialRepository;
import iot.lviv.ua.domain.Credential;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CredentialController {
    @Autowired
    CredentialRepository credentialRepository;

    @GetMapping("/addresses")
    public List<Credential> getAllNotes() {
        return credentialRepository.findAll();
    }

    @PostMapping("/addresses")
    public Credential createNote(@Valid @RequestBody Credential credential) {
        return credentialRepository.save(credential);
    }

    @GetMapping("/addresses/{id}")
    public Credential getNoteById(@PathVariable(value = "id") Long credentialId) {
        return credentialRepository.findById(credentialId)
                .orElseThrow(() -> new ResourceNotFoundException("Credential", "id", credentialId));
    }

    @PutMapping("/addresses/{id}")
    public Credential updateNote(@PathVariable(value = "id") Long credentialId,
                              @Valid @RequestBody Credential credentialContent) {

        Credential credential = credentialRepository.findById(credentialId)
                .orElseThrow(() -> new ResourceNotFoundException("Credential", "id", credentialId));

        credential.setPassword(credentialContent.getPassword());
        credential.setLogin(credentialContent.getLogin());
        credential.setTelephone(credentialContent.getTelephone());
        credential.setUserId(credentialContent.getUserId());
        credential.setEmail(credentialContent.getEmail());

        return credentialRepository.save(credential);
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long credentialId) {
        Credential credential = credentialRepository.findById(credentialId)
                .orElseThrow(() -> new ResourceNotFoundException("Credential", "id", credentialId));

        credentialRepository.delete(credential);

        return ResponseEntity.ok().build();
    }
}
