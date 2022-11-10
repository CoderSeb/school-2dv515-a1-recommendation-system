package lnu.sa224ny.backendassignment1.services;

import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UserRepository userRepository;

    public User getByUsername(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
