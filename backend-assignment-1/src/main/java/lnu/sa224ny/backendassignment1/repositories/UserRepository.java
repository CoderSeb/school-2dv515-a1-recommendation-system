package lnu.sa224ny.backendassignment1.repositories;

import lnu.sa224ny.backendassignment1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
