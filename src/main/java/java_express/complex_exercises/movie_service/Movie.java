package java_express.complex_exercises.movie_service;

import lombok.*;

@Builder
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Movie {
    private String Name;
    private String genre;
    private Double averageRating;
}
