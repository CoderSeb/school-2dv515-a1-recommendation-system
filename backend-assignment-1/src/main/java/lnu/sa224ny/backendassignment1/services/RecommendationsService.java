package lnu.sa224ny.backendassignment1.services;

import lnu.sa224ny.backendassignment1.models.Rating;
import lnu.sa224ny.backendassignment1.models.Recommendation;
import lnu.sa224ny.backendassignment1.models.SimilarUser;
import lnu.sa224ny.backendassignment1.models.User;
import lnu.sa224ny.backendassignment1.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationsService {
    @Autowired
    private RatingsService ratingsService;

    @Autowired
    private UsersService usersService;

    public List<SimilarUser> getEuclideanTopMatchingUsers(User user, int count) {
        List<SimilarUser> similarUsers = getSimilarUsers(user);
        return new ArrayList<>(similarUsers.subList(similarUsers.size() - count, similarUsers.size()));
    }


    private List<SimilarUser> getSimilarUsers(User user) {
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
        Collections.sort(similarUsers, (o1, o2) -> Float.compare(o1.score, o2.score));
        System.out.println(similarUsers);
        return similarUsers;
    }

    private float euclidean(User userA, User userB) {
        List<Rating> userARatings = ratingsService.getAllByUserId(userA.getId());
        List<Rating> userBRatings = ratingsService.getAllByUserId(userB.getId());
        float similarity = 0;

        int counter = 0;

        for (Rating rA : userARatings) {
            for (Rating rB : userBRatings) {
                if (rA == rB) {
                    similarity += Math.pow((rA.getRating() - rB.getRating()), 2);
                    counter++;
                }
            }
        }

        if (counter == 0) {
            return 0;
        }
        float invertedScore = 1 / (1 + similarity);
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
                if (rA == rB) {
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

        double num = pSum - (sumOne*sumTwo / counter);
        double den = Math.sqrt((sumOneSquared - Math.pow(sumOne, 2) / counter) * (sumTwoSquared - Math.pow(sumTwo, 2) / counter));

        return (float) (num/den);
    }
}
