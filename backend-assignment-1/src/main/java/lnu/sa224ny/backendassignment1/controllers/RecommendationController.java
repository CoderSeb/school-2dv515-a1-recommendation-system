package lnu.sa224ny.backendassignment1.controllers;

import lnu.sa224ny.backendassignment1.dtos.SimUserDTO;
import lnu.sa224ny.backendassignment1.models.Rating;
import lnu.sa224ny.backendassignment1.models.Recommendation;
import lnu.sa224ny.backendassignment1.models.SimilarUser;
import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.repositories.UserRepository;
import lnu.sa224ny.backendassignment1.services.RatingsService;
import lnu.sa224ny.backendassignment1.services.RecommendationsService;
import lnu.sa224ny.backendassignment1.services.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class RecommendationController {
    private UsersService usersService;
    private RatingsService ratingsService;
    private RecommendationsService recommendationsService;

    /*
    @RequestMapping("/api/recommendation")
    public List<Recommendation> recommendations(@RequestParam String user, @RequestParam String method, @RequestParam String similarity) {
        User activeUser = usersService.getByUsername(user);
        List<Recommendation> result;
        if (Objects.equals(method, "ItemBased") && Objects.equals(similarity, "Euclidean")) {
            result = recommendationsService.getEuclideanTopMatchingUsers(activeUser, 3);
        } else {
            result = recommendationsService.getEuclideanTopMatchingUsers(activeUser, 3);
        }
        return result;
    }*/

    @RequestMapping("/api/recommendation/top-matching-users")
    public List<SimUserDTO> topMatchingUsers(@RequestParam String user, @RequestParam String method, @RequestParam String similarity, @RequestParam int count) {
        User activeUser = usersService.getByUsername(user);
        List<SimUserDTO> result = recommendationsService.getEuclideanTopMatchingUsers(activeUser, count).stream().map(usr -> {
            SimUserDTO dtoUsr = new SimUserDTO();
            dtoUsr.id = usr.user.getId();
            dtoUsr.name = usr.user.getName();
            dtoUsr.score = usr.score;
            return dtoUsr;
        }).toList();
        return result;
    }
}
