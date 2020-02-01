package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.TicketPassageRepository;
import iot.lviv.ua.domain.TicketPassage;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class TicketPassageController {
    @Autowired
    TicketPassageRepository ticketPassageRepository;

    @GetMapping("/passageTickets")
    public List<TicketPassage> getAllNotes() {
        return ticketPassageRepository.findAll();
    }

    @PostMapping("/passageTickets")
    public TicketPassage createNote(@Valid @RequestBody TicketPassage passageTicket) {
        return ticketPassageRepository.save(passageTicket);
    }

    @GetMapping("/passageTickets/{id}")
    public TicketPassage getNoteById(@PathVariable(value = "id") Long passageTicketId) {
        return ticketPassageRepository.findById(passageTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("TicketPassage", "id", passageTicketId));
    }

    @PutMapping("/passageTickets/{id}")
    public TicketPassage updateNote(@PathVariable(value = "id") Long passageTicketId,
                              @Valid @RequestBody TicketPassage addressContent) {

        TicketPassage passageTicket = ticketPassageRepository.findById(passageTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("PassageTicket", "id", passageTicketId));

        passageTicket.setRow(addressContent.getRow());
        passageTicket.setNumber(addressContent.getNumber());
        passageTicket.setFree(addressContent.isFree());
        passageTicket.setPrice(addressContent.getPrice());
        passageTicket.setPassageClass(addressContent.getPassageClass());
        passageTicket.setPassageId(addressContent.getPassageId());

        return ticketPassageRepository.save(passageTicket);
    }

    @DeleteMapping("/passageTickets/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long passageTicketId) {
        TicketPassage passageTicket = ticketPassageRepository.findById(passageTicketId)
                .orElseThrow(() -> new ResourceNotFoundException("PassageTicket", "id", passageTicketId));

        ticketPassageRepository.delete(passageTicket);

        return ResponseEntity.ok().build();
    }
}
