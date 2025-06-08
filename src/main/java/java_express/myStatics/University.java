package java_express.myStatics;

public class University {
    static String universityName = "FINEC";
    final int studentID;
    String studentName;

    public University(int studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    public static void changeUniversityName(String name) {
        University.universityName = name;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "University{" +
                "University name=" + University.universityName +
                "studentID=" + studentID +
                ", studentName='" + studentName + '\'' +
                '}';
    }

    public void printStudentInfo() {
        System.out.println(this.toString());
    }
}
