package java_express.ifelse;

import java.util.Scanner;

public class Switchcase {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int x = scanner.nextInt();
//        printNumbersReversed(x);
//
//        scanner.close();

        searchStop();
    }

    static String dayOfWeek(int x) {
        return switch (x) {
            case 1 -> "Понедельник";
            case 2 -> "Вторник";
            case 3 -> "Среда";
            case 4 -> "Четверг";
            case 5 -> "Пятница";
            case 6 -> "Суббота";
            case 7 -> "Воскресенье";
            default -> "Не понятно";
        };
    }

    static String price(int x) {
        return switch (x) {
            case 1, 2, 3, 4, 5 -> "300 рублей";
            case 6, 7 -> "450 рублей";
            default -> "Не понятно";
        };
    }

    static char intToChar(int x) {
        if (x >= 90 && x<= 100) return 'A';
        else if (x >= 80 && x <= 89) return 'B';
        else if (x >= 70 && x <= 79) return 'C';
        else if (x >= 60 && x <= 69) return 'D';
        return 'F';

    }

    static String operations(String s) {
        return switch (s) {
            case "start" -> "Система запущена";
            case "stop" -> "Система остановлена";
            case "restart" -> "Система перезапущена";
            case "status" -> "Статус ОК";
            default -> "Неизвестная команда";
        };
    }

    static Integer math(int x, int y, String op) {
        if (y == 0 && op.equals("/")) throw new ArithmeticException();
        return switch (op) {
            case "+" -> x + y;
            case "-" -> x - y;
            case "*" -> x * y;
            case "/" -> x / y;
            default -> null;
        };
    }

    static void print100() {
        for (int i = 1; i <= 100; i++) {
            System.out.println(i);
        }
    }

    static void sumOf100(int n) {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        System.out.println(sum);
    }

    static void table(int x) {
        for (int i = 2; i < 9; i++) {
            System.out.println("""
                    %d * %d = %d
                    """.formatted(x, i, (x * i)));
        }
    }

    static void isPrime(int x) {
        boolean isPrime = true;

        for (int i = 2; i < x; i++) {
            if (x % i == 0) isPrime = false;
        }

        if (isPrime) System.out.println("Число положительное");
        else System.out.println("Число отрицательное");

    }

    static void printNumbers() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
        }
    }

    static void fact(int x) {
        int r = 1;
        int i = 1;

        while (i != x) {
            r *= i;
            i++;
        }
        System.out.println(r);
    }

    static void printOdd(int x) {
        int i = 1;

        while (i <= x) {
            if (i % 2 == 0) System.out.println(i);

            i++;
        }
    }

    static void printNumbersReversed(int x) {
        while (x >= 1) {
            System.out.println(x);
            x--;
        }
    }

    static void searchPositive() {
        Scanner scanner = new Scanner(System.in);
        int x;
        do {
            x = scanner.nextInt();
        } while (x <= 0);

        System.out.println("Число положительное: " + x);

        scanner.close();
    }

    static void checkPass() {
        String pass = "qwerty";
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            input = scanner.nextLine();
        } while (!(input.equals(pass)));

        System.out.println("ok");
        scanner.close();


    }

    static void anotherPrint() {
        int i = 0;

        do {
            i++;
            System.out.println(i);
        } while (i < 10);
    }

    static void waitExit() {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            input = scanner.nextLine();
        } while (!(input.equals("exit")));

        scanner.close();
    }

    static void counOfDigits() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int counter = 0;

        do {
            n /= 10;
            counter++;
        } while (n > 0);

        System.out.println(counter);

        scanner.close();
    }

    static void inputNums() {
        Scanner scanner = new Scanner(System.in);
        int y = 0;
        int sum = 0;

        while (true) {
            y = scanner.nextInt();
            if (y < 0) break;
            sum += y;
        }

        System.out.println(sum);

        scanner.close();
    }

    static void iHate3() {
        for (int i = 0; i <= 20; i++) {
            if (i % 3 == 0) continue;
            System.out.println(i);
        }
    }

    static void onlyPositive() {
        Scanner scanner = new Scanner(System.in);
        int x;

        while (true) {
            x = scanner.nextInt();
            if (x < 0) continue;

            System.out.println(x);
        }
    }

    static void searchStop() {
        Scanner scanner = new Scanner(System.in);
        String s = "";

        while (true) {
            s = scanner.nextLine();
            if (s.equals("stop")) break;

            System.out.println(s);
        }
    }
}
