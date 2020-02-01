package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.PassageRepository;
import iot.lviv.ua.domain.Passage;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PassageController {
    @Autowired
    PassageRepository passageRepository;

    @GetMapping("/passages")
    public List<Passage> getAllNotes() {
        return passageRepository.findAll();
    }

    @PostMapping("/passages")
    public Passage createNote(@Valid @RequestBody Passage passage) {
        return passageRepository.save(passage);
    }

    @GetMapping("/passages/{id}")
    public Passage getNoteById(@PathVariable(value = "id") Long passageId) {
        return passageRepository.findById(passageId)
                .orElseThrow(() -> new ResourceNotFoundException("Passage", "id", passageId));
    }

    @PutMapping("/passages/{id}")
    public Passage updateNote(@PathVariable(value = "id") Long passageId,
                              @Valid @RequestBody Passage addressContent) {

        Passage passage = passageRepository.findById(passageId)
                .orElseThrow(() -> new ResourceNotFoundException("Passage", "id", passageId));

        passage.setArrivalCity(addressContent.getArrivalCity());
        passage.setDepartureCity(addressContent.getDepartureCity());
        passage.setarrivalTime(addressContent.getArrivalTime());
        passage.setDepartureTime(addressContent.getDepartureTime());
        passage.setNumber(addressContent.getNumber());
        passage.setPassageType(addressContent.getPassageType());
        passage.setCompany(addressContent.getCompany());

        return passageRepository.save(passage);
    }

    @DeleteMapping("/passages/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long passageId) {
        Passage passage = passageRepository.findById(passageId)
                .orElseThrow(() -> new ResourceNotFoundException("Passage", "id", passageId));

        passageRepository.delete(passage);

        return ResponseEntity.ok().build();
    }
}
