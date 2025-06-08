package java_express.myObjects;

public class Car {
    private String brand;
    private int year;

    public Car(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    public String getBrand() {return this.brand;}
    public int getYear() {return this.year;}

    public void setBrand(String brand) {this.brand = brand;}
    public void setYear(int year) {this.year = year;}

    public void print() {
        System.out.println("brand: " + brand + "\n" +
        "year: " + year);
    }
}
