package java_express.complex_exercises.entity_manager;

import com.github.javafaker.Faker;
import java_express.complex_exercises.entity_manager.model.EntityAbstractModel;
import java_express.complex_exercises.entity_manager.model.PersonModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ObjectStreamField;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityManagerTest {
    EntityManager<PersonModel> entityManager;
    PersonModel person;
    PersonModel anotherPerson;
    Faker faker;


    // нужно ли проверять конструкторы хэшкод и тд
    // нужно ли проверять кейсы, и как если да, где код даже не компилируется -> пытаемся добавить невалидный объект, а код не компилируется


    @BeforeEach
    void setupTest() {
        entityManager = new EntityManager<>();
        person = new PersonModel("Ivan", 20, true);
        anotherPerson = new PersonModel();
        faker = new Faker();
    }

    /**
     * add()
     * Позитив:
     * добавить 1 элемент -> true
     * добавить 1 элемент -> проверяем список на наличие этого элемента
     * добавить 1 элемент -> проверяем список на наличие 1 элемента
     */

    @Test
    void userAddItemAndGetTrue() {
        boolean actualResult = entityManager.add(person);
        assertTrue(actualResult);
    }

    @Test
    void userAddItemToEmptyListAndListShouldHaveJustOneItem() {
        entityManager.add(person);
        int actualResult = entityManager.getAll().size();
        int expectedResult = 1;

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void userAddItemToEmptyListAndListShouldHaveSameObject() {
        entityManager.add(person);
        PersonModel actualResult = entityManager.getAll().getFirst();
        assertEquals(person, actualResult);
    }

    /**
     * remove()
     * позитив:
     * список с 1 элементом, удаляю элемент -> список пустой
     * список с 1 элементом, удаляю элемент -> вернулось true
     * список с 2 элементами, удаляю 1 элемент -> список с 1 элементом
     * список с 2 элементами, удаляю 1 элемент -> остается элемент, который я не удалял
     * список с 1 элементом, удаляю элемент, которого нет -> список остался не измененным
     */

    @Test
    void userRemoveValidElementAndGetTrue() {
        entityManager.add(person);
        boolean actualResult = entityManager.remove(person);
        assertTrue(actualResult);
    }

    @Test
    void userRemoveValidElementAndListBecameBlank() {
        entityManager.add(person);
        entityManager.remove(person);
        boolean actualResult = entityManager.getAll().isEmpty();
        assertTrue(actualResult);
    }

    @Test
    void userRemoveValidElementInAListOfTwoAndListSizeEqualsOne() {
        entityManager.add(person);
        entityManager.add(anotherPerson);
        entityManager.remove(anotherPerson);
        int actualResult = entityManager.getAll().size();
        assertEquals(1, actualResult);
    }

    @Test
    void userRemoveValidElementInAListOfTwoAndListHaveValidElement() {
        entityManager.add(person);
        entityManager.add(anotherPerson);
        entityManager.remove(anotherPerson);
        PersonModel actualResult = entityManager.getAll().getFirst();
        assertEquals(person, actualResult);
    }

    @Test
    void userRemoveInvalidElementAndListNotMutate() {
        entityManager.add(person);
        List<PersonModel> expectedResult = entityManager.getAll();
        entityManager.remove(new PersonModel());
        List<PersonModel> actualResult = entityManager.getAll();
        assertEquals(expectedResult, actualResult);
    }

    /**
     * getAll()
     * Список с двумя элементами -> Вернулся список с двумя элементами
     * Пустой список -> Вернулся пустой список
     */

    @Test
    void methodShouldReturnListWithTwoElements() {
        entityManager.add(person);
        entityManager.add(anotherPerson);

        int actualResult = entityManager.getAll().size();

        assertEquals(2, actualResult);
    }

    @Test
    void methodShouldReturnEmptyList() {
        int actualResult = entityManager.getAll().size();
        assertEquals(0, actualResult);
    }

    /**
     * filterByAge()
     * [10,15,20,25,30] > 15,25 -> [15,20,25]
     * [10,15,20,25,30] > 150,250 -> []
     */

    @Test
    void methodShouldReturnFilteredListWithValidValues() {
        int start = 15;
        int end = 25;
        for (int age : List.of(10,15,20,25,30))
            entityManager.add(new PersonModel(faker.name().firstName(), age));

        List<PersonModel> actualResult = entityManager.filterByAge(start, end);
        List<PersonModel> expectedResult = entityManager.getAll().stream()
                .filter(person -> person.getAge() >= start && person.getAge() <= end)
                .toList();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void listHaveNotValuesIfInputOffsetMethodAndShouldReturnEmptyList() {
        int start = 150;
        int end = 250;
        for (int age : List.of(10,15,20,25,30))
            entityManager.add(new PersonModel(faker.name().firstName(), age));

        List<PersonModel> actualResult = entityManager.filterByAge(start, end);

        assertTrue(actualResult.isEmpty());
    }

    /**
     * filterByName()
     * [John, Peter, John, Joe] > John -> [John]
     * [John, Peter, John, Joe] > Sarah -> []
     */

    @Test
    void shouldReturnListWithJohnValues() {
        for (String name : new String[]{"John", "Peter", "John", "Joe"})
            entityManager.add(new PersonModel(name, 25));
        boolean hadNotJohnName = true;

        List<PersonModel> filteredList = entityManager.filterByName("John");

        for (PersonModel personModel : filteredList)
            if (!(personModel.getName().equals("John"))) hadNotJohnName = false;
        assertTrue(hadNotJohnName);
    }

    /**
     * filterByActivity()
     * [true, false] > true -> [true]
     * [] > true -> []
     */

    @Test
    void shouldReturnListWithActivityTrueValues() {
        for (Boolean activity : new boolean[]{true, false})
            entityManager.add(new PersonModel(faker.name().firstName(), faker.number().randomDigit(), activity));
        boolean hadNotFalseValues = true;

        List<PersonModel> filteredList = entityManager.filterByActivity(true);

        for (PersonModel personModel : filteredList)
            if (!(personModel.getActive())) hadNotFalseValues = false;
        assertTrue(hadNotFalseValues);
    }

    @Test
    void shouldReturnEmptyList() {
        List<PersonModel> filteredList = entityManager.filterByActivity(true);
        assertTrue(filteredList.isEmpty());
    }


}