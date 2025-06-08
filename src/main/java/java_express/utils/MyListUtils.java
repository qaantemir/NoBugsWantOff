package java_express.utils;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class MyListUtils {

    public static <T> ArrayList<T> createList(int count, Class<T> tclass) {
        Faker faker = new Faker();
        ArrayList<T> list = new ArrayList<>();
        if (tclass == String.class) {
            for (int i = 0; i < count; i++) {
                list.add((T) faker.pokemon().name());
            }
        } else if (tclass == Integer.class) {
            for (int i = 0; i < count; i++) {
                Integer integerF = Integer.valueOf(faker.number().randomDigit());
                if (list.contains(integerF)) {
                    i--;
                    continue;
                }
                list.add((T) integerF);
            }
        } else {
            throw new IllegalArgumentException();
        }

        return list;
    }

    public static <E> void printList(List<E> list) {
        String result = "[";

        List<E> newL = new ArrayList<>(list);

        while (!(newL.isEmpty())) {
            E ele = newL.getFirst();
            String el = String.valueOf(ele);
            if (newL.size() == 1) {
                result = result + el;
                break;
            }
            else result = result + el + ", ";
            newL.removeFirst();
        }

        result = result + "]";

        System.out.println(result);

    }
}
