package java_express.complex_exercises.movie_service;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    /**
     *     «Интерстеллар» (фантастика)
     *     «Пираты» (приключения)
     *     «Гладиатор» (исторический)
     *     «Драйв» (триллер)
     *     «Матрица» (антиутопия)
     */

    /**
     * добавление оценки:
     * добавить 3 оценки каждому фильму -> оценки добавлены
     * <p>
     * расчет средней:
     * расчитать среднюю по каждому фильму -> средние получены
     * <p>
     * сортировка:
     * получение сортированного списка фильмов == храние -> сортированный список
     */


    MovieService movieService;
    Map<Movie, List<Rating>> movieListMap;
    Movie m1, m2, m3;

    @BeforeEach
    void setupTest() {
        movieService = new MovieService();

        movieListMap = movieService.getMovieListMap();

        m1 = Movie.builder()
                .Name("Интерстеллар")
                .genre("Фантастика")
                .build();

        m2 = Movie.builder()
                .Name("Пираты")
                .genre("Приключения")
                .build();

        m3 = Movie.builder()
                .Name("Гладиатор")
                .genre("Исторический")
                .build();

        movieListMap.put(m1, new ArrayList<Rating>());
        movieListMap.put(m2, new ArrayList<Rating>());
        movieListMap.put(m3, new ArrayList<Rating>());



    }

    @Test
    void userAddThreeGradesToEachMovie() {
        double g1 = Faker.instance().number().randomDouble(2, 1, 10);
        double g2 = Faker.instance().number().randomDouble(2, 1, 10);
        double g3 = Faker.instance().number().randomDouble(2, 1, 10);

        movieService.addGrade(m1, g1);
        movieService.addGrade(m1, g2);
        movieService.addGrade(m1, g3);

        List<Rating> expectedResult = new ArrayList<>(){{
            add(new Rating<>(g1));
            add(new Rating<>(g2));
            add(new Rating<>(g3));
        }};
        List<Rating> actualResult = new ArrayList<>(movieService.getMovieListMap().get(m1));
        assertEquals(expectedResult, actualResult);

    }

    @Test
    void userShouldGetMovieAvgRating() {
        double g1 = Faker.instance().number().randomDouble(2, 1, 10);
        double g2 = Faker.instance().number().randomDouble(2, 1, 10);
        double g3 = Faker.instance().number().randomDouble(2, 1, 10);

        movieService.addGrade(m1, g1);
        movieService.addGrade(m1, g2);
        movieService.addGrade(m1, g3);

        double expectedResult = (g1 + g2 + g3) / 3;
        double actualResult = movieService.getAvgRating(m1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void userShouldGetSortedMovieList() {
        double g1 = Faker.instance().number().randomDouble(2, 1, 10);
        double g2 = Faker.instance().number().randomDouble(2, 1, 10);
        double g3 = Faker.instance().number().randomDouble(2, 1, 10);
        double g4 = Faker.instance().number().randomDouble(2, 1, 10);
        double g5 = Faker.instance().number().randomDouble(2, 1, 10);
        double g6 = Faker.instance().number().randomDouble(2, 1, 10);
        double g7 = Faker.instance().number().randomDouble(2, 1, 10);
        double g8 = Faker.instance().number().randomDouble(2, 1, 10);
        double g9 = Faker.instance().number().randomDouble(2, 1, 10);

        movieService.addGrade(m1, g1);
        movieService.addGrade(m1, g2);
        movieService.addGrade(m1, g3);

        movieService.addGrade(m2, g4);
        movieService.addGrade(m2, g5);
        movieService.addGrade(m2, g6);

        movieService.addGrade(m3, g7);
        movieService.addGrade(m3, g8);
        movieService.addGrade(m3, g9);

        Map<Movie, List<Rating>> expectedResult = new TreeMap<>(
                (x, y) -> x.getAverageRating().compareTo(y.getAverageRating()));

        for (var entry : movieService.getMovieListMap().entrySet())
            expectedResult.put(entry.getKey(),entry.getValue());


    }
}