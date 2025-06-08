package java_express.myObjects;

public class Circle {
    private double radius;

    public Circle(double radius) {
        if (radius > 0) {
            this.radius = radius;
        }
        else {
            System.out.println("Нельзя определить отрицательным или нулевым значением");
        }
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double calculateArea() {
        return 3.14 * (this.radius * this.radius);
    }

    public double calculateCircumference() {
        return 2 * 3.14 * this.radius;
    }
}
