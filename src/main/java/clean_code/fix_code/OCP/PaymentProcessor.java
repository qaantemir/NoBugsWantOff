package clean_code.fix_code.OCP;

/**
 * Нарушение OCP (Open/Closed Principle) – закрытый для расширения код
 * Задача: Избавьтесь от if-else, применив полиморфизм (наследование или интерфейсы).
 */
public class PaymentProcessor {

    public void processPayment(Payment payment, double amount) {
        payment.doPayment(amount);
    }

}
