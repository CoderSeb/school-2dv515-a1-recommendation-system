package lnu.sa224ny.backendassignment1.controllers;

import lnu.sa224ny.backendassignment1.models.Recommendation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class RecommendationController {
    @RequestMapping("/api/recommendation")
    public List<Recommendation> recommendations(@RequestParam String user, @RequestParam String method, @RequestParam String similarity) {
        System.out.println(user);
        Recommendation rec1 = new Recommendation("Shaw Shank", 1, 2.334);
        Recommendation rec2 = new Recommendation("Shaw Shank 2", 2, 2.534);
        List<Recommendation> result = new ArrayList<>();
        result.add(rec1);
        return result;
    }

}
