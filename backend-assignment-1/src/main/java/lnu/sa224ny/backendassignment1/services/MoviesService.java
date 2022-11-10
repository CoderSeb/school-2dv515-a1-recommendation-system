package lnu.sa224ny.backendassignment1.services;

import lnu.sa224ny.backendassignment1.models.Movie;
import lnu.sa224ny.backendassignment1.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie getById(int movieId) {
        Optional<Movie> foundMovie = movieRepository.findById(movieId);

        return foundMovie.orElse(null);
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }
}
