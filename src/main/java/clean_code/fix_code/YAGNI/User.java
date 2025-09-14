package clean_code.fix_code.YAGNI;

/**
 * Нарушение YAGNI (You Ain't Gonna Need It) – ненужный код
 * Задача: Удалите неиспользуемые поля и оставьте только необходимые данные.
 */
public class User {
    private String name;
    private String email;
    private String phoneNumber;
    public User(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
