package java_express.complex_exercises.inventory_service;

import lombok.*;

@EqualsAndHashCode
@ToString
@Builder
@Getter
@Setter
public class Product implements Comparable {
    private String name;
    private Integer price;
    private String category;

    @Override
    public int compareTo(Object o) {
        Product that = (Product) o;
        return this.price.compareTo(that.getPrice());
    }
}
