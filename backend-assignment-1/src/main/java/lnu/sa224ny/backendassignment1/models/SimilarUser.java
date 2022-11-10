package lnu.sa224ny.backendassignment1.models;

public class SimilarUser {
    public User user;
    public float score;

    @Override
    public String toString() {
        return "Name: " + user.getName() + ", score: " + score;
    }
}
