package java_express.ifelse;

import java.util.Scanner;

public class Ifelse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();

        System.out.println(
                testResults(i)
        );

        scanner.close();

    }

    public static String checkPos(int x) {
        if (x > 0) return "Положительное";
        else if (x == 0) return "Число равно нулю";
        return "Отрицательное";
    }

    public static int max(int x, int y) {
        if (x > y) return x;
        return y;
    }

    static String checkSmth(int x) {
        return switch (x) {
            case 1, 2 -> "Неудовлетворительно";
            case  3 -> "Удовлетворительно";
            case 4 -> "Хорошо";
            case 5 -> "Отлично";
            default -> "Не известное значение";
        };
    }

    static String checkOdd(int x) {
        return (x % 2 == 0) ? "Четное" : "Нечетное";
    }

    static double discount(int i) {
        if (i < 18) return 0.25;
        else if (i >= 65) return 0.30;
        return 0.0;
    }

    static String testResults(int x) {
        if (x < 60) return "Неудовлетворительно";
        else if (x >= 60 && x <= 74) return "Удовлетворительно";
        else if (x >= 75 && x <= 89) return "Хорошо";
        return "Отлично";
    }

}
