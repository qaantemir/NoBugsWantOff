package java_express.myObjects;

public class Main {
    public static void main(String[] args) {

        // проверяю методы Car
        Car car = new Car("Audi", 2000);
        car.setBrand("Tesla");
        car.setYear(2025);
        car.print();

        // проверяю методы Rectangle
        Rectangle rectangle = new Rectangle(10, 2);
        rectangle.setWidth(5);
        System.out.println(rectangle.calculateArea());

        // проверяю методы Book
        Book book = new Book("Наруто", "Миядзаки");
        book.setAuthor("Кисимото");
        book.printInfo();

        // проверяю методы BankAccount
        BankAccount bankAccount = new BankAccount("Пукин", 0);
        bankAccount.deposit(1000.0d);
        bankAccount.withdraw(100.0);
        bankAccount.printBalance();

        // проверяю методы Point
        Point point = new Point(2, 1);
        point.setX(100);
        point.print();

        // проверяю методы StudentGroup
        StudentGroup studentGroup = new StudentGroup("Humans", 5000);
        studentGroup.setGroupCount(10000);
        studentGroup.printInfo();

        // проверяю методы Circle
        Circle circle = new Circle(10);
        circle.setRadius(100);
        System.out.println(circle.calculateCircumference());
        System.out.println(circle.calculateArea());

        // проверяю методы Teacher
        Teacher teacher = new Teacher("Марьиванна", "Информатика");
        teacher.setSubject("Математика");
        teacher.printInfo();

        // проверяю методы Product
        Product product = new Product("Apple", 100);
        product.setPrice(1000);
        product.applyDiscount(100);
        product.printInfo();

        // проверяю методы Laptop
        Laptop laptop = new Laptop("Lenovo", 300000);
        laptop.setPrice(29000);
        laptop.printInfo();
    }


}
