package lnu.sa224ny.backendassignment1.services.workerModels;

public class MovieRecommendation {
    private String movieName;
    private int movieId;
    private double score;

    public MovieRecommendation(String movieName, int movieId, double score) {
        this.movieName = movieName;
        this.movieId = movieId;
        this.score = score;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

