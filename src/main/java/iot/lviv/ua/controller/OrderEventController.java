package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.OrderEventRepository;
import iot.lviv.ua.domain.OrderEvent;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderEventController {
    @Autowired
    OrderEventRepository orderEventRepository;

    @GetMapping("/orderedEvents")
    public List<OrderEvent> getAllNotes() {
        return orderEventRepository.findAll();
    }

    @PostMapping("/orderedEvents")
    public OrderEvent createNote(@Valid @RequestBody OrderEvent orderEvent) {
        return orderEventRepository.save(orderEvent);
    }

    @GetMapping("/orderedEvents/{id}")
    public OrderEvent getNoteById(@PathVariable(value = "id") Long orderEventId) {
        return orderEventRepository.findById(orderEventId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderEvent", "id", orderEventId));
    }

    @PutMapping("/orderedEvents/{id}")
    public OrderEvent updateNote(@PathVariable(value = "id") Long orderEventId,
                              @Valid @RequestBody OrderEvent addressContent) {

        OrderEvent orderEvent = orderEventRepository.findById(orderEventId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderEvent", "id", orderEventId));

        orderEvent.setPayed(addressContent.isPayed());
        orderEvent.setUserId(addressContent.getUserId());
        orderEvent.setDelivery(addressContent.getDelivery());
        orderEvent.setPaymentMethod(addressContent.getPaymentMethod());
        orderEvent.setEventId(addressContent.getEventId());

        return orderEventRepository.save(orderEvent);
    }

    @DeleteMapping("/orderedEvents/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long orderEventId) {
        OrderEvent orderEvent = orderEventRepository.findById(orderEventId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderEvent", "id", orderEventId));

        orderEventRepository.delete(orderEvent);

        return ResponseEntity.ok().build();
    }
}
