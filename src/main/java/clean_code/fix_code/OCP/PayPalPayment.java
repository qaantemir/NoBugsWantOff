package clean_code.fix_code.OCP;

public class PayPalPayment extends Payment {

    @Override
    public void doPayment(double amount) {
        System.out.println("Оплата PayPal на сумму " + amount);
    }

}
