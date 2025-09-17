package clean_code.patterns_java.builder_orders;

import clean_code.patterns_java.abstract_fabric_gui.Button;

import java.util.List;

public class Order {
    private List<String> goodsList;
    private double discount;
    private String paymentType;

    public Order(List<String> goodsList, double discount, String paymentType) {
        this.goodsList = goodsList;
        this.discount = discount;
        this.paymentType = paymentType;
    }

    public List<String> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<String> goodsList) {
        this.goodsList = goodsList;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    static class Builder {
        private List<String> goodsList;
        private double discount;
        private String paymentType;

        public Builder goodsList(List<String> goodsList) {
            this.goodsList = goodsList;
            return this;
        }

        public Builder discount(double discount) {
            this.discount = discount;
            return this;
        }

        public Builder paymentType(String paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        public Order build() {
            return new Order(
                    this.goodsList,
                    this.discount,
                    this.paymentType
            );
        }
    }

}
