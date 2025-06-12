package java_express.complex_exercises.grade_service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class StudentGrade <T extends Number> {
    private String name;
    private String subject;
    private T grade;
}