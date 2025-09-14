package clean_code.fix_code.DRY;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Нарушение DRY (Don't Repeat Yourself) – дублирование кода
 * Задача: Устраните дублирование кода, применив перегрузку методов или использование массива аргументов.
 */
public class MathOperations {
    public int add(int a, int b) {
        return a + b;
    }
    public int addThreeNumbers(int a, int b, int c) {
        return a + b + c;
    }
    public int addFourNumbers(int a, int b, int c, int d) {
        return a + b + c + d;
    }

    public int add(int... ints) {
        return Arrays.stream(ints).sum();
    }

    public static void main(String[] args) {
        var myInts = new MathOperations();
        System.out.println(myInts.add(new int[]{1, 2}));
    }
}
