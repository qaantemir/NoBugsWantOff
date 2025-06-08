package java_express.myStatics;

public class Person {
    private String firstName;
    private String lastName;
    private final String ssn;

    public Person(String ssn, String lastName, String firstName) {
        this.ssn = ssn;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void prinInfo() {
        System.out.println("имя: " + firstName + ", фамилия: " + lastName + ", страховка " + ssn);
    }
}
