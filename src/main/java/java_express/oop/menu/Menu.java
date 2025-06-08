package java_express.oop.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    List<Dish> menu = new ArrayList<>();

    public void add(Dish dish) {
         menu.add(dish);
    }

    public void printMenu() {
        menu.forEach(System.out::println);
    }
}
