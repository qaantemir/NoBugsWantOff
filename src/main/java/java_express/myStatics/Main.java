package java_express.myStatics;

import com.github.javafaker.Faker;

public class Main {
    public static void main(String[] args) {
        Company company1 = new Company(1, "Fedor");
        Company company2 = new Company(2, "John");
        Company company3 = new Company(3, "Peter");
        Company company4 = new Company(4, "Ivan");

        System.out.println("company name is " + Company.companyName);

        Company.companyName = "Yesbugs";

        System.out.println("company name is " + Company.companyName);

        //company1.employeeID = 10;

        System.out.println(MathConstants.calculateCircleArea(10));
        System.out.println(MathConstants.calculateCircumference(100));


        Library l = new Library();
        l.setAuthor("Koko");
        l.setCategory("Thriller");
        l.setBookTitle("Horror");
        l.setYear(1984);

        System.out.println(l.getAuthor());
        System.out.println(l.getBookTitle());
        System.out.println(l.getCategory());
        System.out.println(l.getYear());

        l.author = "Petya";
        l.year = 2000;
        l.category = "Comedy";


        University student1 = new University(1, "Ivan");
        University student2 = new University(2, "Anna");
        University student3 = new University(3, "Polina");

        University.changeUniversityName("SPBGEU");

        student1.printStudentInfo();
        student2.printStudentInfo();
        student3.printStudentInfo();

        GameSettings game1 = new GameSettings("wow", 0);
        GameSettings game2 = new GameSettings("BDO", 5);

        GameSettings.maxPlayers = 1000;

        game1.addPlayer();
        game1.addPlayer();
        game1.addPlayer();
        game2.addPlayer();
        game2.addPlayer();
        game2.addPlayer();

        game1.printGameStatus();
        game2.printGameStatus();

        Faker faker = new Faker();

        Person person1 = new Person(faker.idNumber().ssnValid(), faker.name().lastName(), faker.name().firstName());
        Person person2 = new Person(faker.idNumber().ssnValid(), faker.name().lastName(), faker.name().firstName());

        person1.setFirstName(faker.name().firstName());

        person1.prinInfo();
        person2.prinInfo();

    }
}
