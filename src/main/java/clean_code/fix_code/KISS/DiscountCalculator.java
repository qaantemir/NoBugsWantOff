package clean_code.fix_code.KISS;

/**
 * Нарушение KISS (Keep It Simple, Stupid) – чрезмерно сложный код
 * Задача: Упростите код, убрав вложенные условия, сделав его более читаемым и поддерживаемым.
 */
public class DiscountCalculator {

    public double calculateDiscount(double price, boolean isLoyalCustomer, boolean isFirstPurchase, boolean hasCoupon) {
        double discount = 0.0;

        if (isLoyalCustomer) {
            if (isFirstPurchase) {
                discount = price * 0.10;
            } else {
                discount = price * 0.05;
            }
        } else {
            if (hasCoupon) {
                discount = price * 0.07;
            } else {
                discount = price * 0.02;
            }
        }
        return price - discount;
    }

    public double newCalculateDiscount(double price, boolean isLoyalCustomer, boolean isFirstPurchase, boolean hasCoupon) {
        double discount = 0.0;

        if (isLoyalCustomer && isFirstPurchase) {
            discount = price * 0.10;
        } else if (isLoyalCustomer && !isFirstPurchase) {
            discount = price * 0.05;
        } else if (hasCoupon) {
            discount = price * 0.07;
        } else {
            discount = price * 0.02;
        }

        return price - discount;
    }
}
