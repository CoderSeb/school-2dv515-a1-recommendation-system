package lnu.sa224ny.backendassignment1.services;

import lnu.sa224ny.backendassignment1.dtos.SimUserDTO;
import lnu.sa224ny.backendassignment1.models.Movie;
import lnu.sa224ny.backendassignment1.models.Rating;
import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.services.workerModels.MovieRecommendation;
import lnu.sa224ny.backendassignment1.services.workerModels.SimilarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {
    @Autowired
    private RatingsService ratingsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MoviesService moviesService;

    public List<SimUserDTO> getEuclideanTopMatchingUsers(User user, int count) {
        List<SimilarUser> similarUsers = getEuclideanSimilarUsers(user);
        return similarUsers.subList(0, count).stream().map(this::convertSimUserToDTO).collect(Collectors.toList());
    }

    public List<SimUserDTO> getPearsonTopMatchingUsers(User user, int count) {
        List<SimilarUser> similarUsers = getPearsonSimilarUsers(user);
        return similarUsers.subList(0, count).stream().map(this::convertSimUserToDTO).collect(Collectors.toList());
    }

    private SimUserDTO convertSimUserToDTO(SimilarUser user) {
        SimUserDTO newSimDTO = new SimUserDTO();
        newSimDTO.name = user.user.getName();
        newSimDTO.id = user.user.getId();
        newSimDTO.score = user.score;
        return newSimDTO;
    }

    public List<MovieRecommendation> getEuclideanRecommendedMovies(User user, int count) {
        List<MovieRecommendation> result = new ArrayList<>();
        List<SimilarUser> similarUsers = getEuclideanSimilarUsers(user);
        return getMovieRecommendations(count, result, similarUsers, user.getId());
    }

    private double getWeightedScore(Movie movie, SimilarUser user, double weight) {
        Rating unWeightedRating = ratingsService.getByMovieAndUserId(movie.getId(), user.user.getId());
        if (unWeightedRating != null) {
            return unWeightedRating.getRating() * weight;
        }
        return 0;
    }


    public List<MovieRecommendation> getPearsonRecommendedMovies(User user, int count) {
        List<MovieRecommendation> result = new ArrayList<>();
        List<SimilarUser> similarUsers = getPearsonSimilarUsers(user);
        return getMovieRecommendations(count, result, similarUsers, user.getId());
    }

    private List<MovieRecommendation> getMovieRecommendations(int count, List<MovieRecommendation> result, List<SimilarUser> similarUsers, int userId) {
        List<Movie> allMovies = moviesService.getAll();
        List<Rating> allUserRatings = ratingsService.getAllByUserId(userId);

        for (Movie movie : allMovies) {
            if (!moviePresent(allUserRatings, movie)) {
                double weightedSum = 0;
                double similaritySum = 0;
                for (SimilarUser simUsr : similarUsers) {
                    double weightedScore = getWeightedScore(movie, simUsr, simUsr.score);
                    if (weightedScore != 0 && simUsr.score > 0) {
                        weightedSum += weightedScore;
                        similaritySum += simUsr.score;
                    }
                }
                double weightedScore = weightedSum / similaritySum;
                MovieRecommendation newRecommendation = new MovieRecommendation(movie.getTitle(), movie.getId(), weightedScore);
                result.add(newRecommendation);
            }
        }
        result.sort(Comparator.comparing(MovieRecommendation::getScore).reversed());
        return result.subList(0, count);
    }

    private boolean moviePresent(List<Rating> userRatings, Movie movie) {
        for (Rating rating : userRatings) {
            if (rating.getMovieId() == movie.getId()) {
                return true;
            }
        }
        return false;
    }


    private List<SimilarUser> getEuclideanSimilarUsers(User user) {
        List<User> userList = usersService.getAll();
        List<SimilarUser> similarUsers = new ArrayList<>();
        for (User usr : userList) {
            if (usr != user) {
                SimilarUser simUser = new SimilarUser();
                simUser.user = usr;
                simUser.score = euclidean(user, usr);
                similarUsers.add(simUser);
            }
        }
        similarUsers.sort(Comparator.comparing(SimilarUser::getScore).reversed());

        return similarUsers;
    }

    private List<SimilarUser> getPearsonSimilarUsers(User user) {
        List<User> userList = usersService.getAll();
        List<SimilarUser> similarUsers = new ArrayList<>();
        for (User usr : userList) {
            if (usr != user) {
                SimilarUser simUser = new SimilarUser();
                simUser.user = usr;
                simUser.score = pearson(user, usr);
                similarUsers.add(simUser);
            }
        }
        similarUsers.sort(Comparator.comparing(SimilarUser::getScore).reversed());

        return similarUsers;
    }

    private double euclidean(User userA, User userB) {
        List<Rating> userARatings = ratingsService.getAllByUserId(userA.getId());
        List<Rating> userBRatings = ratingsService.getAllByUserId(userB.getId());

        double similarity = 0;

        int counter = 0;

        for (Rating rA : userARatings) {
            for (Rating rB : userBRatings) {
                if (rA.getMovieId() == rB.getMovieId()) {
                    similarity += Math.pow((rA.getRating() - rB.getRating()), 2);
                    counter++;
                }
            }
        }

        if (counter == 0) {
            return 0;
        }

        double invertedScore = 1 / (1 + similarity);

        return invertedScore;
    }

    private float pearson(User userA, User userB) {
        List<Rating> userARatings = ratingsService.getAllByUserId(userA.getId());
        List<Rating> userBRatings = ratingsService.getAllByUserId(userB.getId());

        double sumOne, sumTwo, sumOneSquared, sumTwoSquared, pSum;
        sumOne = sumTwo = sumOneSquared = sumTwoSquared = pSum = 0;
        int counter = 0;
        for (Rating rA : userARatings) {
            for (Rating rB : userBRatings) {
                if (rA.getMovieId() == rB.getMovieId()) {
                    sumOne += rA.getRating();
                    sumTwo += rB.getRating();
                    sumOneSquared += Math.pow(rA.getRating(), 2);
                    sumTwoSquared += Math.pow(rB.getRating(), 2);
                    pSum += rA.getRating() * rB.getRating();
                    counter++;
                }
            }
        }
        if (counter == 0) {
            return 0;
        }

        double num = pSum - (sumOne * sumTwo / counter);
        double den = Math.sqrt((sumOneSquared - Math.pow(sumOne, 2) / counter) * (sumTwoSquared - Math.pow(sumTwo, 2) / counter));

        return (float) (num / den);
    }


}
