package lnu.sa224ny.backendassignment1.services;

import lnu.sa224ny.backendassignment1.models.Rating;
import lnu.sa224ny.backendassignment1.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingsService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAllByUserId(int user_id) {
        return ratingRepository.findByUser_Id(user_id);
    }

    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    public Rating getByMovieAndUserId(int movieId, int userId) {
        return ratingRepository.findByMovie_IdAndUser_Id(movieId, userId);
    }
}
