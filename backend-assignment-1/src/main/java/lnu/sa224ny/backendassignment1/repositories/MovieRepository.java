package lnu.sa224ny.backendassignment1.repositories;

import lnu.sa224ny.backendassignment1.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
