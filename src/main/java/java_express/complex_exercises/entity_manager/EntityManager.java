package java_express.complex_exercises.entity_manager;

import java_express.complex_exercises.entity_manager.model.EntityAbstractModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityManager<T extends EntityAbstractModel> {
    private CopyOnWriteArrayList<T> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public boolean add(T entity) {
        return copyOnWriteArrayList.add(entity);
    }

    public boolean remove(T entity) {
        return copyOnWriteArrayList.remove(entity);
    }

    public List<T> getAll() {
        return List.copyOf((copyOnWriteArrayList));
    }

    public List<T> filterByAge(int start, int end) {
        return copyOnWriteArrayList.stream().filter(e -> e.getAge() >= start && e.getAge() <= end).toList();
    }

    public List<T> filterByName(String name) {
        return copyOnWriteArrayList.stream().filter(e -> e.getName().equals(name)).toList();
    }

    public List<T> filterByActivity(boolean isActive) {
        return copyOnWriteArrayList.stream().filter(e -> e.getActive().equals(isActive)).toList();
    }

}
