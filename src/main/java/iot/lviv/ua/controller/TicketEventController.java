package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.TicketEventRepository;
import iot.lviv.ua.domain.TicketEvent;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketEventController {
    @Autowired
    TicketEventRepository ticketEventRepository;

    @GetMapping("/eventTickets")
    public List<TicketEvent> getAllNotes() {
        return ticketEventRepository.findAll();
    }

    @PostMapping("/eventTickets")
    public TicketEvent createNote(@Valid @RequestBody TicketEvent eventTicket) {
        return ticketEventRepository.save(eventTicket);
    }

    @GetMapping("/eventTickets/{id}")
    public TicketEvent getNoteById(@PathVariable(value = "id") Long eventTicketId) {
        return ticketEventRepository.findById(eventTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("TicketEvent", "id", eventTicketId));
    }

    @PutMapping("/eventTickets/{id}")
    public TicketEvent updateNote(@PathVariable(value = "id") Long eventTicketId,
                              @Valid @RequestBody TicketEvent addressContent) {

        TicketEvent eventTicket = ticketEventRepository.findById(eventTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("TicketEvent", "id", eventTicketId));

        eventTicket.setNumber(addressContent.getNumber());
        eventTicket.setRow(addressContent.getRow());
        eventTicket.setPrice(addressContent.getPrice());
        eventTicket.setFree(addressContent.isFree());
        eventTicket.setPlace(addressContent.getPlace());
        eventTicket.setEventId(addressContent.getEventId());

        return ticketEventRepository.save(eventTicket);
    }

    @DeleteMapping("/eventTickets/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long eventTicketId) {
        TicketEvent eventTicket = ticketEventRepository.findById(eventTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("TicketEvent", "id", eventTicketId));

        ticketEventRepository.delete(eventTicket);

        return ResponseEntity.ok().build();
    }
}
