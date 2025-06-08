package java_express.troubleshooting;

import java.math.BigDecimal;
import java.util.*;

/**
 * Код должен напечатать все элементы массива, но вместо этого выбрасывает исключение.
 * Проблема: вышли за пределы массива
 */
class DebugTask1 {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }
    }
}

/**
 * Код должен вычислить сумму чисел от 1 до 5, но почему-то результат неправильный.
 * Проблема: использовали декремент вместо инкремента
 */
class DebugTask2 {
    public static void main(String[] args) {
        int sum = calculateSum(5);
        System.out.println("Sum: " + sum);
    }
    public static int calculateSum(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }
}

/**
 * Код должен напечатать числа от 1 до 5, но программа зависает.
 * Проблема: не выполняли условие выхода из цикла, не увеличивали счетчик
 */
class DebugTask3 {
    public static void main(String[] args) {
        int i = 1;
        while (i <= 5) {
            System.out.println("Number: " + i);
            i++;
        }
    }
}

/**
 * Код должен проверить, является ли строка палиндромом, но выбрасывает NullPointerException.
 * Проблема: мы передавали null в метод.
 */
class DebugTask4 {
    public static void main(String[] args) {
        System.out.println(isPalindrome("ahah"));
    }
    public static boolean isPalindrome(String str) {
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
}

/**
 * Код должен увеличить возраст человека, но почему-то возраст остаётся прежним.
 * Проблема: меняли возраст не того же объекта Person, который передавали в метод updateAge(), а создавали нового
 */
class DebugTask5 {
    public static void main(String[] args) {
        Person person = new Person("Alice", 25);
        updateAge(person);
        System.out.println("Updated age: " + person.getAge());
    }
    public static void updateAge(Person person) {
//        person = new Person(person.getName(), person.getAge() + 1);
        person.setAge(person.getAge() + 1);
    }
}
class Person {
    private String name;
    private int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public String getName() { return name; }
    public int getAge() { return age; }

    public void setAge(int age) {
        this.age = age;
    }
}

/**
 * Код должен напечатать числа от n до 1, но программа падает с StackOverflowError.
 *  Проблема: не было условия выхода из рекурсии
 */
class DebugTask6 {
    public static void main(String[] args) {
        countdown(5);
    }
    public static void countdown(int n) {
        System.out.println(n);
        if (n != 1) countdown(n - 1);
    }
}

/**
 * Два потока списывают деньги со счёта одновременно, но почему-то баланс становится отрицательным.
 * Проблема: потоки обращались к withdraw() несогласовано, переменная баланса не была волатильной
 */
class DebugTask7 {
    private static volatile int balance = 100;
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> withdraw(60));
        Thread t2 = new Thread(() -> withdraw(50));
        t1.start();
        t2.start();
    }
    public synchronized static void withdraw(int amount) {
        if (balance >= amount) {
            try { Thread.sleep(100); } catch (InterruptedException e) { }
            balance -= amount;
            System.out.println("New balance: " + balance);
        }
    }
}

/**
 * Код должен сравнить два числа, но почему-то результат не соответствует ожиданиям.
 * Проблема: в java есть проблема с потерей точности у чисел с плавающей точкой, решается например при помощи BigDecimal
 */
class DebugTask8 {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("0.1").multiply(new BigDecimal("3"));
        BigDecimal b = new BigDecimal("0.3");
        if (a.equals(b)) {
            System.out.println("Equal");
        } else {
            System.out.println("Not Equal");
        }
    }
}

/**
 * Код должен проверить, равны ли две строки, но почему-то не работает.
 * Проблема: == сравнивает объекты не по значению, а по ссылке. Нужно использовать equals.
 */
class DebugTask9 {
    public static void main(String[] args) {
        String str1 = new String("hello");
        String str2 = new String("hello");
        if (str1.equals(str2)) {
            System.out.println("Equal");
        } else {
            System.out.println("Not Equal");
        }
    }
}

/**
 * Код должен удалять элементы списка, но выбрасывает ConcurrentModificationException.
 * Проблема: изменяем список по которому итерируемся
 */

class DebugTask10 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));
        for (String name : new ArrayList<>(names)) {
            if (name.startsWith("A")) {
                names.remove(name);
            }
        }
    }
}
