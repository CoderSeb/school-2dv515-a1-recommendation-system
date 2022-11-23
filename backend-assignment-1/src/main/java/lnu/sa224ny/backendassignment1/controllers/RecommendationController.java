package lnu.sa224ny.backendassignment1.controllers;

import lnu.sa224ny.backendassignment1.DTOs.MovieRecommendationDTO;
import lnu.sa224ny.backendassignment1.DTOs.RecommendationRequestDTO;
import lnu.sa224ny.backendassignment1.DTOs.SimilarUserDTO;
import lnu.sa224ny.backendassignment1.enums.SIMILARITY;
import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.services.RecommendationsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class RecommendationController {
    private RecommendationsService recommendationsService;
    private UserController userController;


    @RequestMapping("/api/recommendation")
    public List<MovieRecommendationDTO> getMovieRecommendations(RecommendationRequestDTO request) {
        User activeUser = userController.getUser(request.getUserId());
        List<MovieRecommendationDTO> result;
        if (Objects.equals(request.getSimilarity(), SIMILARITY.Euclidean.label)) {
            result = recommendationsService.getEuclideanRecommendedMovies(activeUser, request.getCount());
        } else {
            result = recommendationsService.getPearsonRecommendedMovies(activeUser, request.getCount());
        }
        return result;
    }

    @RequestMapping("/api/recommendation/top-matching-users")
    public List<SimilarUserDTO> topMatchingUsers(RecommendationRequestDTO request) {
        User activeUser = userController.getUser(request.getUserId());

        List<SimilarUserDTO> result = null;
        if (Objects.equals(request.getSimilarity(), SIMILARITY.Euclidean.label)) {
            result = recommendationsService.getEuclideanTopMatchingUsers(activeUser, request.getCount());
        } else if (Objects.equals(request.getSimilarity(), SIMILARITY.Pearson.label)) {
            result = recommendationsService.getPearsonTopMatchingUsers(activeUser, request.getCount());
        }

        return result;
    }
}
