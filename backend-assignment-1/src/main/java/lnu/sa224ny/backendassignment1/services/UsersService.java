package lnu.sa224ny.backendassignment1.services;

import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsersService {

    private UserRepository userRepository;


    public User getById(int userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        return foundUser.orElse(null);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
