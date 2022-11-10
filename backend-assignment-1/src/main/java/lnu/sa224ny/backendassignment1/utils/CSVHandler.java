package lnu.sa224ny.backendassignment1.utils;

import lnu.sa224ny.backendassignment1.models.Movie;
import lnu.sa224ny.backendassignment1.models.Rating;
import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.repositories.MovieRepository;
import lnu.sa224ny.backendassignment1.repositories.RatingRepository;
import lnu.sa224ny.backendassignment1.repositories.UserRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class CSVHandler {
    private final String pathToCSVFiles;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    public CSVHandler(
            String pathToCSVFiles,
            UserRepository userRepository,
            MovieRepository movieRepository,
            RatingRepository ratingRepository) {
        this.pathToCSVFiles = pathToCSVFiles;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    public void loadCSVsIntoDb() throws FileNotFoundException {
        ratingRepository.deleteAll();
        movieRepository.deleteAll();
        userRepository.deleteAll();
        createUsers();
        createMovies();
        createRatings();
    }

    private List<String> readCSV(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        scanner.useDelimiter(";");


        List<String> records = new ArrayList<>();

        while (scanner.hasNext()) {
            records.add(scanner.nextLine());
        }
        scanner.close();
        return records;
    }

    private void createUsers() throws FileNotFoundException {
        userRepository.deleteAll();
        List<String> rawData = readCSV(pathToCSVFiles + "/users.csv");
        Iterator<String> rawDataIt = rawData.iterator();
        boolean skippedFirst = false;
        while (rawDataIt.hasNext()) {
            if (skippedFirst) {
                String[] lineData = rawDataIt.next().split(";");
                if (userRepository.findById(parseInt(lineData[0])).isEmpty()) {
                    userRepository.save(new User(parseInt(lineData[0]), lineData[1]));
                }

            } else {
                rawDataIt.next();
                skippedFirst = true;
            }
        }
    }

    private void createMovies() throws FileNotFoundException {
        movieRepository.deleteAll();
        List<String> rawData = readCSV(pathToCSVFiles + "/movies.csv");
        Iterator<String> rawDataIt = rawData.iterator();
        boolean skippedFirst = false;
        while (rawDataIt.hasNext()) {
            if (skippedFirst) {
                String[] lineData = rawDataIt.next().split(";");
                if (movieRepository.findById(parseInt(lineData[0])).isEmpty()) {
                    movieRepository.save(new Movie(
                            parseInt(lineData[0]),
                            lineData[1].replace("\"", ""),
                            parseInt(lineData[2])));
                }

            } else {
                rawDataIt.next();
                skippedFirst = true;
            }
        }
    }

    private void createRatings() throws FileNotFoundException {
        List<String> rawData = readCSV(pathToCSVFiles + "/ratings.csv");
        Iterator<String> rawDataIt = rawData.iterator();
        boolean skippedFirst = false;

        while (rawDataIt.hasNext()) {
            if (skippedFirst) {
                String[] lineData = rawDataIt.next().split(";");
                Optional<User> foundUser = userRepository.findById(parseInt(lineData[0]));
                Optional<Movie> foundMovie = movieRepository.findById(parseInt(lineData[1]));
                if (foundUser.isPresent() && foundMovie.isPresent()) {
                    ratingRepository.save(new Rating(foundUser.get(), foundMovie.get(), parseDouble(lineData[2])));
                }
            } else {
                rawDataIt.next();
                skippedFirst = true;
            }
        }

    }
}
