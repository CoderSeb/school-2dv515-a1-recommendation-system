package lnu.sa224ny.backendassignment1.services.workerModels;

import lnu.sa224ny.backendassignment1.models.User;

import java.util.List;

public class SimilarUser {
    public User user;
    public double score;

    public List<WeightedRating> ratings;

    @Override
    public String toString() {
        return "Name: " + user.getName() + ", score: " + score;
    }

    public double getScore() {
        return score;
    }
}
