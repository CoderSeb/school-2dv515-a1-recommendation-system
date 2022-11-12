package lnu.sa224ny.backendassignment1.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lnu.sa224ny.backendassignment1.dtos.RecommendationRequestDTO;
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
    public List<MovieRecommendation> recommendations(RecommendationRequestDTO request) {
        User activeUser = usersService.getByUsername(request.getUserName());
        List<MovieRecommendation> result;
        if (Objects.equals(request.getSimilarity(), SIMILARITY.Euclidean.label)) {
            result = recommendationsService.getEuclideanRecommendedMovies(activeUser, request.getCount());
        } else {
            result = recommendationsService.getPearsonRecommendedMovies(activeUser, request.getCount());
        }
        return result;
    }

    @RequestMapping("/api/recommendation/top-matching-users")
    public List<SimUserDTO> topMatchingUsers(RecommendationRequestDTO request) {
        User activeUser = usersService.getByUsername(request.getUserName());

        List<SimUserDTO> result = null;
        if (Objects.equals(request.getSimilarity(), SIMILARITY.Euclidean.label)) {
            result = recommendationsService.getEuclideanTopMatchingUsers(activeUser, request.getCount());
        } else if (Objects.equals(request.getSimilarity(), SIMILARITY.Pearson.label)) {
            result = recommendationsService.getPearsonTopMatchingUsers(activeUser, request.getCount());
        }

        return result;
    }
}
