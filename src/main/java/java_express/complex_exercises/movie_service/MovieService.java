package java_express.complex_exercises.movie_service;

import com.github.javafaker.Demographic;
import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public class MovieService {
    private volatile Map<Movie, List<Rating>> movieListMap = new HashMap<>();

    public synchronized <T extends Number> void addGrade(Movie movie, T grade) {
        double wrappedGrade = (double) grade;
        if (wrappedGrade < 1 || wrappedGrade > 10) throw new IllegalArgumentException();
        Rating<T> rating = new Rating<>(grade);
        // todo реализовать подсчет средней на этом этапе
        movieListMap.get(movie).add(rating);
    }

    public double getAvgRating(Movie movie) {
        List<Rating> ratingList = movieListMap.get(movie);
        int length = ratingList.size();
        double sum = ratingList.stream()
                .mapToDouble(n -> n.getGrade().doubleValue())
                .sum();

        return (double) sum / length;
    }


    public List<Movie> sortByRating() {
        return this.movieListMap.keySet().stream()
                        .peek(movie -> {
                            var avg = this.getAvgRating(movie);
                            movie.setAverageRating(avg);
                        })
                        .toList();
    }

}
