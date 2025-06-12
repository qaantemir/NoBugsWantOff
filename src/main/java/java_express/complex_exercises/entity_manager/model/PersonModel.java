package java_express.complex_exercises.entity_manager.model;

public class PersonModel extends EntityAbstractModel {
    public PersonModel() {
    }

    public PersonModel(String name, Integer age, Boolean isActive) {
        super(name, age, isActive);
    }

    public PersonModel(String name, Integer age) {
        super(name, age);
    }
}
