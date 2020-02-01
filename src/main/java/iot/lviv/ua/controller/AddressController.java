package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.AddressRepository;
import iot.lviv.ua.domain.Address;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/addresses")
    public List<Address> getAllNotes() {
        return addressRepository.findAll();
    }

    @PostMapping("/addresses")
    public Address createNote(@Valid @RequestBody Address address) {
        return addressRepository.save(address);
    }

    @GetMapping("/addresses/{id}")
    public Address getNoteById(@PathVariable(value = "id") Integer addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
    }

    @PutMapping("/addresses/{id}")
    public Address updateNote(@PathVariable(value = "id") Integer addressId,
                           @Valid @RequestBody Address addressContent) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        address.setCity(addressContent.getCity());
        address.setStreet(addressContent.getStreet());
        address.setNumber(addressContent.getNumber());

        return addressRepository.save(address);
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Integer addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        addressRepository.delete(address);

        return ResponseEntity.ok().build();
    }
}
