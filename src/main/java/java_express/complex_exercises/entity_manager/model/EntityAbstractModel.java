package java_express.complex_exercises.entity_manager.model;

import java.util.Objects;

public abstract class EntityAbstractModel {
    private String name;
    private Integer age;
    private Boolean isActive;

    public EntityAbstractModel() {
    }

    public EntityAbstractModel(String name, Integer age, Boolean isActive) {
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    public EntityAbstractModel(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.isActive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EntityAbstractModel entity)) return false;
        return Objects.equals(name, entity.name) && Objects.equals(age, entity.age) && Objects.equals(isActive, entity.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isActive);
    }
}
