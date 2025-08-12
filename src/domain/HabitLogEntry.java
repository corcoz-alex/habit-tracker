package domain;

import java.io.Serializable;
import java.time.LocalDate;

public class HabitLogEntry implements Serializable {
    private final LocalDate date;
    public HabitLogEntry(LocalDate date) { this.date = date; }
    public LocalDate getDate() { return date; }
    @Override public String toString() { return date.toString(); }
}
