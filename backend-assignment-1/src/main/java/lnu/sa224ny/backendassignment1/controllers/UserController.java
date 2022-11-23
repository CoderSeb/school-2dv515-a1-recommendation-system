package lnu.sa224ny.backendassignment1.controllers;

import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class UserController {

    private UsersService usersService;

    @RequestMapping("/api/users")
    public List<User> users() {
        return usersService.getAll();
    }

    @RequestMapping("/api/user/{userId}")
    public User getUser(@PathVariable int userId) {
        return usersService.getById(userId);
    }

}
