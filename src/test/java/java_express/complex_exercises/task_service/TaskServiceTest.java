package java_express.complex_exercises.task_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    TaskService<Integer> taskService;
    Task<Integer> task1;
    Task<Integer> task2;
    Task<Integer> task3;

    @BeforeEach
    void setupTest() {
        taskService = new TaskService<>();

        task1 = Task.<Integer>builder()
                .id(1)
                .status(Status.BACKLOG)
                .priority(Priority.LOW)
                .date(LocalDate.parse("2001-01-01"))
                .build();

        task2 = Task.<Integer>builder()
                .id(2)
                .status(Status.IN_PROGRESS)
                .priority(Priority.MEDIUM)
                .date(LocalDate.parse("2002-02-02"))
                .build();

        task3 = Task.<Integer>builder()
                .id(3)
                .status(Status.DONE)
                .priority(Priority.HIGH)
                .date((LocalDate.parse("2003-03-03")))
                .build();

    }

    @Test
    void taskSetUpdatedIfUserAddTaskWithSameId() {
        Integer id = 1;

        Task<Integer> task1 = Task.<Integer>builder()
                .id(id)
                .status(Status.BACKLOG)
                .build();

        Task<Integer> task2 = Task.<Integer>builder()
                .id(id)
                .status(Status.DONE)
                .build();


        taskService.addTask(task1);
        taskService.addTask(task2);
        assertTrue(taskService.getTaskSet().size() == 1);
        taskService.removeTask(1);
        assertTrue(taskService.getTaskSet().isEmpty());

        for (Task<Integer> integerTask : taskService.getTaskSet()) {
            System.out.println(integerTask);
        }


    }

    @Test
    void userShouldGetSortedByPriorityList() {
        for (var task : List.of(task1, task2, task3)) taskService.addTask(task);
        List<Task<Integer>> expectedResult = List.of(task3, task2, task1);
        List<Task<Integer>> actualResult = taskService.getSortedTaskListByPriority();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void userShouldGetFilteredAndSortedByPriorityList() {
        Priority priority = Priority.HIGH;
        Task<Integer> task4 = Task.<Integer>builder()
                .id(999)
                .priority(priority)
                .build();
        for (var task : List.of(task1, task2, task3, task4)) taskService.addTask(task);
        List<Task<Integer>> expectedResult = List.of(task3, task4);
        List<Task<Integer>> actualResult = taskService.getFilteredTaskListByPriority(priority);

        assertTrue(actualResult.containsAll(expectedResult));
    }


    @Test
    void userShouldGetSortedByStatusList() {
        for (var task : List.of(task1, task2, task3)) taskService.addTask(task);
        List<Task<Integer>> expectedResult = List.of(task3, task2, task1);
        List<Task<Integer>> actualResult = taskService.getSortedTaskListByStatus();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void userShouldGetFilteredByStatusList() {
        Status status = Status.DONE;
        Task<Integer> task4 = Task.<Integer>builder()
                .id(999)
                .status(status)
                .build();
        for (var task : List.of(task1, task2, task3, task4)) taskService.addTask(task);
        List<Task<Integer>> expectedResult = List.of(task3, task4);
        List<Task<Integer>> actualResult = taskService.getFilteredTaskListByStatus(status);

        assertEquals(expectedResult, actualResult);
    }


    @Test
    void userShouldGetSortedByDateList() {
        for (var task : List.of(task1, task2, task3)) taskService.addTask(task);
        List<Task<Integer>> expectedResult = List.of(task1, task2, task3);
        List<Task<Integer>> actualResult = taskService.getSortedTaskListByDate();

        assertEquals(expectedResult, actualResult);
    }

}