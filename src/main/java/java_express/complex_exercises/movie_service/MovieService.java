package java_express.complex_exercises.movie_service;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class MovieService {
    @Getter
    private volatile Map<Movie, List<Rating>> movieAndRatingsListMap = new TreeMap<>();

    public synchronized void addRating(Movie movie, Rating rating) {
        if (rating == null && (rating.getRating().doubleValue() < 1. || rating.getRating().doubleValue() > 10.))
            throw new IllegalArgumentException();

        if (!this.movieAndRatingsListMap.containsKey(movie))
            movieAndRatingsListMap.put(movie, new ArrayList<>());

        List<Rating> ratingList = this.movieAndRatingsListMap.get(movie);

        ratingList.add(rating);

        double avgRating = ratingList.stream()
                .mapToDouble(r -> r.getRating().doubleValue())
                .average().getAsDouble();

        movie.setAverageRating(avgRating);
    }

    public synchronized double getAverageRating(Movie movie) {
        return movie.getAverageRating();
    }

    public synchronized TreeSet<Movie> getSortedByRatingSet() {
        return new TreeSet<>(this.movieAndRatingsListMap.keySet());
    }
}
