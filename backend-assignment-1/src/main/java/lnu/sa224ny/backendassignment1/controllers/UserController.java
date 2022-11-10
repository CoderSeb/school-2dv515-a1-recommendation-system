package lnu.sa224ny.backendassignment1.controllers;

import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;

    @RequestMapping("/api/users")
    public List<User> users() {
        return userRepository.findAll();
    }
}
