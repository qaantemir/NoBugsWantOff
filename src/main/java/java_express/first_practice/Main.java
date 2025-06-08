package java_express.first_practice;

public class Main {

    public static void main(String[] args) {
        System.out.println(MathOperations.add(1, 2));
        System.out.println(MathOperations.subtract(2, 1));
        System.out.println(MathOperations.multyply(5, 2));
        System.out.println(MathOperations.divide(10, 5));

        System.out.println("findMax" + MathOperations.findMax(9, 3));

        System.out.println("difference: " + MathOperations.difference(3, 5));

        System.out.println("squareArea: " + MathOperations.squareArea(2));
        System.out.println("squarePerimeter: " + MathOperations.squarePerimeter(2));

        System.out.println("convertSecondsToMinutes: " + MathOperations.convertSecondsToMinutes(120));

        System.out.println("averageSpeed: " + MathOperations.averageSpeed(10, 5));

        System.out.println("findHypotenuse: " + MathOperations.findHypotenuse(3, 4));

        System.out.println("circleCircumference: " + MathOperations.circleCircumference(23));

        System.out.println("calculatePercentage: " + MathOperations.calculatePercentage(100, 10));

        System.out.println("celsiusToFahrenheit: " + MathOperations.celsiusToFahrenheit(10));

        System.out.println("fahrenheitToCelsius: " + MathOperations.fahrenheitToCelsius(100));


    }
}