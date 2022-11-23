package lnu.sa224ny.backendassignment1.controllers;

import lnu.sa224ny.backendassignment1.models.Movie;
import lnu.sa224ny.backendassignment1.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class MovieController {
    private MovieRepository movieRepository;

    @RequestMapping("/api/movies")
    public List<Movie> movies() {
        return movieRepository.findAll();
    }
}
