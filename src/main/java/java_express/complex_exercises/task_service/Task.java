package java_express.complex_exercises.task_service;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@ToString
@Builder
@Getter
public class Task <T> {
    @NonNull
    private final T id;
    @Setter
    private Status status;
    @Setter
    private Priority priority;
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task<?> task)) return false;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}


