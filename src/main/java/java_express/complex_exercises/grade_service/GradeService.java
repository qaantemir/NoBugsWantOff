package java_express.complex_exercises.grade_service;

import java.util.ArrayList;
import java.util.List;

public class GradeService <T extends Number> {
    private volatile List<StudentGrade<T>> studentGradeList = new ArrayList<>();

    public synchronized void addGrade(StudentGrade sg) throws InvalidGradeException {
        if (sg.getGrade().doubleValue() < 0) throw new InvalidGradeException();
        studentGradeList.add(sg);
    }

    public synchronized double getaAvgStudentGrade(String subject) {
        List<StudentGrade<T>> filteredList = studentGradeList.stream()
                .filter(sg -> sg.getSubject().equals(subject))
                .toList();
        double sum = 0;
        int studentGradeListLength = studentGradeList.size();

        for (StudentGrade grade : filteredList)
            sum += grade.getGrade().doubleValue();

        return sum / studentGradeListLength;
    }

    public synchronized List<StudentGrade<T>> getStudentGradeList() {
        return this.studentGradeList;
    }
}