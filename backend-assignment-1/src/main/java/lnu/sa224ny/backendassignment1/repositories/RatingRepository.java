package lnu.sa224ny.backendassignment1.repositories;

import lnu.sa224ny.backendassignment1.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUser_Id(int user_id);

    Rating findByMovie_IdAndUser_Id(int movieId, int userId);
}
