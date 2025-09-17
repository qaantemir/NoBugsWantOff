package clean_code.patterns_java.builder_orders;

import java.util.List;

public class Shop {

    public static void main(String[] args) {
        Order o = new Order.Builder()
                .goodsList(List.of("pc"))
                .discount(0.2)
                .paymentType("usdt")
                .build();

        if (o instanceof Order) {
            System.out.println(o.getGoodsList());
            System.out.println(o.getDiscount());
            System.out.println(o.getPaymentType());
        }
    }
}
