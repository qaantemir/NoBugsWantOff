package clean_code.fix_code.OCP;

public class CreditCardPayment extends Payment {

    @Override
    public void doPayment(double amount) {
        System.out.println("Оплата CreditCard на сумму " + amount);
    }
}
