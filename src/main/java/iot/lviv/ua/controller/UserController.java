package iot.lviv.ua.controller;

import iot.lviv.ua.Repository.UserRepository;
import iot.lviv.ua.domain.User;
import iot.lviv.ua.exeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllNotes() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createNote(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}")
    public User getNoteById(@PathVariable(value = "id") Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @PutMapping("/users/{id}")
    public User updateNote(@PathVariable(value = "id") Long userId,
                              @Valid @RequestBody User addressContent) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(addressContent.getName());
        user.setSurname(addressContent.getSurname());

        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }
}
