package clean_code.fix_code.DIP;

/**
 * Нарушение DIP (Dependency Inversion Principle) – жесткая зависимость от конкретных классов
 * Задача: Используйте интерфейсы и внедрение зависимостей, чтобы ослабить связь между классами.
 */
public class EmailSender implements Sendable {
    public void send(String message) {
        System.out.println("Отправка email: " + message);
    }

}
