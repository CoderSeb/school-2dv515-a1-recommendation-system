package lnu.sa224ny.backendassignment1.controllers;

import lnu.sa224ny.backendassignment1.dtos.SIMILARITY;
import lnu.sa224ny.backendassignment1.dtos.SimUserDTO;
import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.services.RecommendationsService;
import lnu.sa224ny.backendassignment1.services.UsersService;
import lnu.sa224ny.backendassignment1.services.workerModels.MovieRecommendation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class RecommendationController {
    private UsersService usersService;
    private RecommendationsService recommendationsService;


    @RequestMapping("/api/recommendation")
    public List<MovieRecommendation> recommendations(@RequestParam String user, @RequestParam String method, @RequestParam String similarity, @RequestParam int count) {
        User activeUser = usersService.getByUsername(user);
        List<MovieRecommendation> result;
        if (Objects.equals(similarity, SIMILARITY.Euclidean.label)) {
            result = recommendationsService.getEuclideanRecommendedMovies(activeUser, count);
        } else {
            result = recommendationsService.getPearsonRecommendedMovies(activeUser, count);
        }
        return result;
    }

    @RequestMapping("/api/recommendation/top-matching-users")
    public List<SimUserDTO> topMatchingUsers(@RequestParam String user, @RequestParam String method, @RequestParam String similarity, @RequestParam int count) {
        User activeUser = usersService.getByUsername(user);

        List<SimUserDTO> result = null;
        if (Objects.equals(similarity, SIMILARITY.Euclidean.label)) {
            result = recommendationsService.getEuclideanTopMatchingUsers(activeUser, count);
        } else if (Objects.equals(similarity, SIMILARITY.Pearson.label)) {
            result = recommendationsService.getPearsonTopMatchingUsers(activeUser, count);
        }

        return result;
    }
}
