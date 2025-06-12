package java_express.complex_exercises.movie_service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {
    MovieService movieService;
    Map<Movie, List<Rating>> movieAndRatingsListMap;

    @BeforeEach
    void setupTest() {
        movieService = new MovieService();
        movieAndRatingsListMap = movieService.getMovieAndRatingsListMap();

    }


    @Test
    void userShouldAddRatingToMovie() {
        Movie movie = new Movie("Интерстеллар");
        Rating<Integer> expectedResult = new Rating<>(5);

        movieAndRatingsListMap.put(movie, new ArrayList<Rating>());
        movieService.addRating(movie, expectedResult);

        Rating<Integer> actualResult = movieService.getMovieAndRatingsListMap().get(movie).getFirst();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void averageRatingShouldCalculatedAfterAddMovie() {
        Movie movie = new Movie("Интерстеллар");
        Rating<Integer> rating1 = new Rating<>(1);
        Rating<Integer> rating2 = new Rating<>(2);
        Rating<Integer> rating3 = new Rating<>(3);

        movieAndRatingsListMap.put(movie, new ArrayList<Rating>());

        for (var rating : List.of(rating1, rating2, rating3))
            movieService.addRating(movie, rating);

        double expectedResult =
                (rating1.getRating().doubleValue()
                        + rating2.getRating().doubleValue()
                        + rating3.getRating().doubleValue())
                        / 3;

        double actualResult = movieService.getAverageRating(movie);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void userShouldGetSortedSet() {
        Movie movie1 = new Movie("Атака титанов");
        Movie movie2 = new Movie("Наруто");
        Movie movie3 = new Movie("Тетрадь смерти");
        Faker faker = Faker.instance();

        for (var movie : List.of(movie1, movie2, movie3)) {
            movieAndRatingsListMap.put(movie, new ArrayList<>());

            for (int i = 0; i < 5; i++) {
                movieService.addRating(movie, new Rating<Integer>(faker.number().numberBetween(1,10)));
            }
        }



        List<Movie> expectedResult = new ArrayList<>(movieAndRatingsListMap.keySet());
        Collections.sort(expectedResult);

        List<Movie> actualResult = movieService.getSortedByRatingSet().stream().toList();

        assertEquals(expectedResult,actualResult);

    }

    @Test
    void userShouldGetRatingToMovieTwice() {
        Movie m1 = new Movie("Наруто");
        Movie m2 = new Movie("Наруто");

        movieService.addRating(m1,new Rating(5));
        movieService.addRating(m2,new Rating(1));

        System.out.println(movieAndRatingsListMap);
    }

    @Test
    void userShouldAddNewMovie() {

    }
}