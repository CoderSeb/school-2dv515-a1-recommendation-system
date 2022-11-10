package lnu.sa224ny.backendassignment1.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @Column(name = "rating")
    private double rating;

    public Rating(User user, Movie movie, double rating) {
        this.user = user;
        this.movie = movie;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public int getUserId() {
        return user.getId();
    }

    public int getMovieId() {
        return movie.getId();
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "user: " + getUserId() + ", movie: " + getMovieId() + ", rating: " + getRating();
    }
}
