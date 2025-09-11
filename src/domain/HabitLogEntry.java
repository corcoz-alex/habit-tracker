package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class HabitLogEntry implements Serializable {
    private final LocalDate date;

    public HabitLogEntry(LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public LocalDate getDate() { return date; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitLogEntry that)) return false;
        return date.equals(that.date);
    }
    @Override public int hashCode() { return date.hashCode(); }

    @Override public String toString() { return date.toString(); }
}
