package lnu.sa224ny.backendassignment1.services;

import lnu.sa224ny.backendassignment1.models.Movie;
import lnu.sa224ny.backendassignment1.repositories.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MoviesService {
    private MovieRepository movieRepository;


    public List<Movie> getAll() {
        return movieRepository.findAll();
    }
}
