package java_express.complex_exercises.inventory_service;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {
    InventoryService inventoryService;
    Faker faker = Faker.instance();

    @BeforeEach
    void setupTests() {
        inventoryService = new InventoryService();
    }

    /**
     * Добавление:
     * Добавить товар с включенным флагом -> Товар добавился
     * Добавить товар с выключенным флагом -> Товар не добавился
     *
     * Получение списка с сортировкой:
     * Добавить 5 товаров одной категории -> Получил список
     * Добавить 5 товаров одной категории -> Сортировкой по цене соблюдена
     * Нет товаров такой категории -> Получил эксепш
     */

    @Test
    void userShouldAddProductWhenOpen() {
        inventoryService.setInventoryOpen(true);
        Product expected = Product.builder()
                .name(faker.pokemon().name())
                .category("Ремонт")
                .price(faker.number().numberBetween(1,100))
                .build();

        inventoryService.addProduct(expected);

        Product actual = inventoryService.getProductsByCategoryMap().get(expected.getCategory()).getFirst();

        assertEquals(expected,actual);
    }

    @Test
    void userShouldAddProductWhenClosed() {
        inventoryService.setInventoryOpen(false);
        Product expected = Product.builder()
                .name(faker.pokemon().name())
                .category("Ремонт")
                .price(faker.number().numberBetween(1,100))
                .build();

        inventoryService.addProduct(expected);

        assertTrue(inventoryService.getProductsByCategoryMap().isEmpty());
    }

    @Test
    @SneakyThrows
    void userShouldGetListByCategory() {
        String category = "Ремонт";
        int expectedAmount = 5;

        for (int i = 0; i < expectedAmount; i++) {
            var product = Product.builder()
                    .name(faker.pokemon().name())
                    .category(category)
                    .price(faker.number().numberBetween(1,100))
                    .build();

            inventoryService.addProduct(product);
        }

        int actualAmount = inventoryService.getProductSortedByPriceList(category).size();

        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    @SneakyThrows
    void userShouldGetSortedList() {
        var category = "Ремонт";
        var p1 = Product.builder()
                .name(faker.pokemon().name())
                .category(category)
                .price(faker.number().numberBetween(1,100))
                .build();
        var p2 = Product.builder()
                .name(faker.pokemon().name())
                .category(category)
                .price(faker.number().numberBetween(1,100))
                .build();
        var p3 = Product.builder()
                .name(faker.pokemon().name())
                .category(category)
                .price(faker.number().numberBetween(1,100))
                .build();
        List<Product> expectedResult = new ArrayList<>();

        for (var product : List.of(p1,p2,p3)) {
            expectedResult.add(product);
            inventoryService.addProduct(product);
        }
        Collections.sort(expectedResult);

        List<Product> actualResult = inventoryService.getProductSortedByPriceList(category);

        assertEquals(expectedResult,actualResult);
    }

    @Test
    void userShouldGetException() {
        assertThrows(OutOfStockException.class,
                () -> inventoryService.getProductSortedByPriceList("Охота"));
    }
}