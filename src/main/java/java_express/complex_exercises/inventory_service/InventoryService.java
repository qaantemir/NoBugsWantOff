package java_express.complex_exercises.inventory_service;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class InventoryService {
    @Setter
    private boolean isInventoryOpen = true;
    private Map<String, List<Product>> productsByCategoryMap = new HashMap<>();

    public void addProduct(Product newProduct) {
        if (!isInventoryOpen) {
            System.out.println("Warehouse is closed");
            return;
        }

        String category = newProduct.getCategory();
        if (!this.productsByCategoryMap.containsKey(category))
            this.productsByCategoryMap.put(category, new ArrayList<Product>());
        List<Product> productList = this.productsByCategoryMap.get(category);
        productList.add(newProduct);
    }

    public List<Product> getProductSortedByPriceList(String category) throws OutOfStockException {
        if(!this.productsByCategoryMap.containsKey(category)
                || this.productsByCategoryMap.get(category).isEmpty()) throw new OutOfStockException();

        List<Product> productListByCategory = this.productsByCategoryMap.get(category);

        return productListByCategory.stream()
                .sorted()
                .toList();
    }
}
