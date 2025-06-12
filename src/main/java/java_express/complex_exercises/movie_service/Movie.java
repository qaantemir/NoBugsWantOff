package java_express.complex_exercises.movie_service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Movie {
    private final String name;
    private Double averageRating;

    public Movie(String name) {
        this.name = name;
        this.averageRating = 0.;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
