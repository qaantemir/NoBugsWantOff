package clean_code.fix_code.OCP;

public class BitcoinPayment extends Payment {

    @Override
    public void doPayment(double amount) {
        System.out.println("Оплата Bitcoin на сумму " + amount);
    }
}
