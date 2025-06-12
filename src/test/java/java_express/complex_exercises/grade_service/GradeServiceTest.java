package java_express.complex_exercises.grade_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeServiceTest {
    GradeService<Integer> gradeService;

    @BeforeEach
    void setupTest() {
        gradeService = new GradeService<>();
    }

    /**
     * add()
     * добавить валидный StudentGrade -> лист+1
     * добавить невалидный StudentGrade с отрицательной оценкой -> экспешн
     *
     * avg()
     * добавить 3 валидных StudentGrade -> получить среднюю
     *
     * многопоточность проверить ?
     *
     */

    @Test
    void userAddValidValueToList() {
        StudentGrade<Integer> expectedResult =
                new StudentGrade<>("Sophy", " Philosophy", 5);
        gradeService.addGrade(expectedResult);
        StudentGrade<Integer> actualResult = gradeService.getStudentGradeList().getLast();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void userAddInvalidValueToList() {
        StudentGrade<Integer> expectedResult =
                new StudentGrade<>("Sophy", " Philosophy", -5);

        assertThrows(InvalidGradeException.class,
                () -> gradeService.addGrade(expectedResult));
    }

    @Test
    void userAddThreeGradesAndGetValidAverageGrade() {
        StudentGrade<Integer> sg1 =
                new StudentGrade<>("Sophy", "Philosophy", 10);
        StudentGrade<Integer> sg2 =
                new StudentGrade<>("Sophy", "Philosophy", 5);
        StudentGrade<Integer> sg3 =
                new StudentGrade<>("Sophy", "Philosophy", 1);

        gradeService.addGrade(sg1);
        gradeService.addGrade(sg2);
        gradeService.addGrade(sg3);

        double actualResult = gradeService.getaAvgStudentGrade("Philosophy");
        double expectedResult = (double) (sg1.getGrade() + sg2.getGrade() + sg3.getGrade()) / 3;

        assertEquals(expectedResult, actualResult);

    }
}