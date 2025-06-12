package java_express.complex_exercises.task_service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskService <T> {
    private volatile Set<Task<T>> taskSet = new HashSet<>();

    public synchronized void addTask(Task<T> task) {
        if (this.taskSet.contains(task)) {
            this.taskSet.remove(task);
        }
        taskSet.add(task);
    }

    public synchronized void removeTask(T id) {
        for (var task : taskSet) {
            if (task.getId().equals(id))
                this.taskSet.remove(task);
        }
    }

    public synchronized Set<Task<T>> getTaskSet() {
        return this.taskSet;
    }
    public synchronized List<Task<T>> getSortedTaskListByPriority() {
        return this.taskSet.stream()
                .sorted((x, y) -> x.getPriority().compareTo(y.getPriority()))
                .toList();
    }

    public synchronized List<Task<T>> getFilteredTaskListByPriority(Priority priority) {
        return this.taskSet.stream()
                .filter(t -> t.getPriority().equals(priority))
                .toList();
    }


    public synchronized List<Task<T>> getSortedTaskListByStatus() {
        return this.taskSet.stream()
                .sorted((x, y) -> x.getStatus().compareTo(y.getStatus()))
                .toList();
    }

    public synchronized List<Task<T>> getFilteredTaskListByStatus(Status status) {
        return this.taskSet.stream()
                .filter(t -> t.getStatus().equals(status))
                .toList();
    }


    public synchronized List<Task<T>> getSortedTaskListByDate() {
        return this.taskSet.stream()
                .sorted((x, y) -> x.getDate().compareTo(y.getDate()))
                .toList();
    }
}
