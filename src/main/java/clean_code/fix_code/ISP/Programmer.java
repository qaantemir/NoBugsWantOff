package clean_code.fix_code.ISP;

/**
 * Нарушение ISP (Interface Segregation Principle) – слишком большой интерфейс
 * Задача: Разделите интерфейс на отдельные специализированные интерфейсы.
 */
public class Programmer implements Workable {

    public void work() {
        System.out.println("Программист пишет код");
    }

}
