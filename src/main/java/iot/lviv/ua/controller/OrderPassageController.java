package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.OrderPassageRepository;
import iot.lviv.ua.domain.OrderPassage;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderPassageController {
    @Autowired
    OrderPassageRepository orderPassageRepository;

    @GetMapping("/orderedPassages")
    public List<OrderPassage> getAllNotes() {
        return orderPassageRepository.findAll();
    }

    @PostMapping("/orderedPassages")
    public OrderPassage createNote(@Valid @RequestBody OrderPassage orderedPassage) {
        return orderPassageRepository.save(orderedPassage);
    }

    @GetMapping("/orderedPassages/{id}")
    public OrderPassage getNoteById(@PathVariable(value = "id") Long orderedPassageId) {
        return orderPassageRepository.findById(orderedPassageId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderPassage", "id", orderedPassageId));
    }

    @PutMapping("/orderedPassages/{id}")
    public OrderPassage updateNote(@PathVariable(value = "id") Long orderedPassageId,
                              @Valid @RequestBody OrderPassage addressContent) {

        OrderPassage orderedPassage = orderPassageRepository.findById(orderedPassageId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderPassage", "id", orderedPassageId));

        orderedPassage.setPayed(addressContent.isPayed());
        orderedPassage.setUserId(addressContent.getUserId());
        orderedPassage.setDelivery(addressContent.getDelivery());
        orderedPassage.setPaymentMethod(addressContent.getPaymentMethod());
        orderedPassage.setPassageId(addressContent.getPassageId());

        return orderPassageRepository.save(orderedPassage);
    }

    @DeleteMapping("/orderedPassages/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long orderedPassageId) {
        OrderPassage orderedPassage = orderPassageRepository.findById(orderedPassageId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderPassage", "id", orderedPassageId));

        orderPassageRepository.delete(orderedPassage);

        return ResponseEntity.ok().build();
    }
}
