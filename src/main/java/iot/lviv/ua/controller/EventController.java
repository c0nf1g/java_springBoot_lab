package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.EventRepository;
import iot.lviv.ua.domain.Event;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/events")
    public List<Event> getAllNotes() {
        return eventRepository.findAll();
    }

    @PostMapping("/events")
    public Event createNote(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    @GetMapping("/events/{id}")
    public Event getNoteById(@PathVariable(value = "id") Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));
    }

    @PutMapping("/events/{id}")
    public Event updateNote(@PathVariable(value = "id") Long eventId,
                              @Valid @RequestBody Event addressContent) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));

        event.setDate(addressContent.getDate());
        event.setDescription(addressContent.getDescription());
        event.setAddressId(addressContent.getAddressId());
        event.setArtistId(addressContent.getArtistId());
        event.setEventType(addressContent.getEventType());

        return eventRepository.save(event);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));

        eventRepository.delete(event);

        return ResponseEntity.ok().build();
    }
}
