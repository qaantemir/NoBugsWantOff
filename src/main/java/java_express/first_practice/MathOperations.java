package java_express.first_practice;

public class MathOperations {

    public static int add(int x, int y) {return x + y;}
    public static int subtract(int x, int y) {return x - y;}
    public static int multyply(int x, int y) {return x * y;}
    public static double divide(int x, int y) {return (double) x / y;}

    public static int findMax(int x, int y) {
        if (x > y) return x;
        return y;
    }

    public static int difference(int x, int y) {
        return Math.abs(x - y);
    }

    public static int squareArea(int x) {return x*x;}

    public static int squarePerimeter(int x) {return x+x+x+x;}

    public static double convertSecondsToMinutes(int seconds) {return (double) seconds / 60;}

    public static double averageSpeed(double distance, double time) {return distance / time;}

    public static double findHypotenuse(double a, double b) {return  Math.hypot(a, b);}

    public static double circleCircumference(double radius) {return 2 * Math.PI * radius;}

    public static double calculatePercentage(double total, double part) {return part / (total / 100);}

    public static double celsiusToFahrenheit(double c) {return c * 9 / 5 + 32;}

    public static double fahrenheitToCelsius(double f) {return (f - 32) * 5 / 9;}

}
