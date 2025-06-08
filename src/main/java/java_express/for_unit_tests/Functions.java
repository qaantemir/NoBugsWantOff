package java_express.for_unit_tests;

import java.util.Arrays;

public class Functions {

    /**
     * Напишите тесты для метода, который определяет, является ли число чётным:
     * Тесты должны проверять:
     * Чётные и нечётные числа
     * Нулевое значение
     * Отрицательные числа
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    /**
     * Напишите тесты для метода, который считает количество гласных букв в строке:
     * Тесты должны проверять:
     * Разные строки ("hello", "java", "AEIOU", "")
     * null (должно выбрасываться исключение)
     * Строки без гласных
     */
    public int countVowels(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return (int) input.toLowerCase().chars()
                .filter(c -> "aeiou".indexOf(c) != -1)
                .count();
    }

    /**
     * Напишите тесты для метода, который переворачивает строку:
     * Тесты должны проверять:
     * Обычные строки
     * Пустую строку
     * null (должно возвращаться null)
     */
    public String reverse(String input) {
        if (input == null) return null;
        return new StringBuilder(input).reverse().toString();
    }

    /**
     * Напишите тесты для метода, который находит максимальное число в массиве:
     * Тесты должны проверять:
     * Обычный массив ([3, 5, 7, 2])
     * Один элемент в массиве
     * Отрицательные числа
     * Пустой массив (должно выбрасываться исключение)
     */
    public int findMax(int[] numbers) {
        return Arrays.stream(numbers).max().orElseThrow();
    }


    /**
     * Напишите тесты для метода, который определяет, является ли год високосным:
     * Тесты должны проверять:
     * Обычные годы
     * Високосные (2020, 2000, 1600)
     * Года, которые делятся на 100, но не на 400 (1900, 2100)
     */
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }


    /**
     * Напишите тесты для метода, который проверяет, является ли строка валидным email:
     * Тесты должны проверять:
     * Корректные и некорректные email ("test@example.com", "bad@.com", "no-at-symbol")
     * null
     */
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }


    /**
     * Напишите тесты для метода, который вычисляет факториал числа:
     * Тесты должны проверять:
     * 0! = 1
     * Маленькие числа (1!, 5!, 7!)
     * Отрицательные числа (должно выбрасываться исключение)
     */
    public int factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative numbers not allowed");
        return (n == 0) ? 1 : n * factorial(n - 1);
    }


    /**
     * Напишите тесты для метода, который находит второе по величине число :
     * Тесты должны проверять:
     * Обычные массивы
     * Массив с одинаковыми числами
     * Один элемент в массиве (должно выбрасываться исключение)
     */
    public int findSecondMax(int[] numbers) {
        return Arrays.stream(numbers).distinct().sorted().skip(numbers.length - 2).findFirst().orElseThrow();
    }


    /**
     * Напишите тесты для метода, который считает количество слов в строке:
     * Тесты должны проверять:
     * <p>
     * Пустую строку
     * null
     * Строку с несколькими пробелами
     */
    public int countWords(String sentence) {
        return sentence.trim().isEmpty() ? 0 : sentence.split("\\s+").length;
    }


    /**
     * Напишите тесты для метода, который проверяет валидность телефонного номера:
     * Тесты должны проверять:
     * <p>
     * Корректные номера ("+1 1234567890")
     * Некорректные номера ("12345", "invalid")
     */
    public boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\+\\d{1,3} \\d{10}");
    }


}
