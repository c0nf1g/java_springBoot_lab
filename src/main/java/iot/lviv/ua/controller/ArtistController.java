package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.ArtistRepository;
import iot.lviv.ua.domain.Artist;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArtistController {
    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/artists")
    public List<Artist> getAllNotes() {
        return artistRepository.findAll();
    }

    @PostMapping("/artists")
    public Artist createNote(@Valid @RequestBody Artist artist) {
        return artistRepository.save(artist);
    }

    @GetMapping("/artists/{id}")
    public Artist getNoteById(@PathVariable(value = "id") Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistId));
    }

    @PutMapping("/artists/{id}")
    public Artist updateNote(@PathVariable(value = "id") Long artistId,
                              @Valid @RequestBody Artist artistContent) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistId));

        artist.setName(artistContent.getName());
        artist.setSurname(artistContent.getSurname());
        artist.setNickname(artistContent.getNickname());

        return artistRepository.save(artist);
    }

    @DeleteMapping("/artists/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", "id", artistId));

        artistRepository.delete(artist);

        return ResponseEntity.ok().build();
    }
}
