package lnu.sa224ny.backendassignment1.services.workerModels;

import lnu.sa224ny.backendassignment1.models.User;

public class SimilarUser {
    public User user;
    public double score;

    @Override
    public String toString() {
        return "Name: " + user.getName() + ", score: " + score;
    }

    public double getScore() {
        return score;
    }
}
