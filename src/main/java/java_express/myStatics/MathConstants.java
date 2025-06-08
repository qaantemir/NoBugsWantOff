package java_express.myStatics;

public class MathConstants {
    final double PI = Math.PI;
    final double E = Math.E;

    public static double calculateCircleArea(double r) {
        return 3.14 * (r * r);
    }

    public static double calculateCircumference(double r) {
        return 2 * 3.14 * r;
    }

}
