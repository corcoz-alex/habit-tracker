package repository;

import domain.Habit;
import domain.HabitLogEntry;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface HabitRepository {
    void save(Map<UUID, Habit> habits, Map<UUID, List<HabitLogEntry>> logs) throws IOException;
    Map<UUID, Habit> loadHabits() throws IOException, ClassNotFoundException;
    Map<UUID, List<HabitLogEntry>> loadLogs() throws IOException, ClassNotFoundException;
}
