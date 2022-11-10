package lnu.sa224ny.backendassignment1.controllers;

import lnu.sa224ny.backendassignment1.models.Rating;
import lnu.sa224ny.backendassignment1.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class RatingController {
    private RatingRepository ratingRepository;

    @RequestMapping("/api/ratings")
    public List<Rating> ratings() {
        return ratingRepository.findAll();
    }
}
