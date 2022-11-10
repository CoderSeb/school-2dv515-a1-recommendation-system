package lnu.sa224ny.backendassignment1.repositories;

import lnu.sa224ny.backendassignment1.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
