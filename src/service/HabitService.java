package service;

import domain.Frequency;
import domain.Habit;
import domain.HabitLogEntry;
import repository.HabitRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class HabitService {
    private final Map<UUID, Habit> habits = new LinkedHashMap<>();
    private final Map<UUID, List<HabitLogEntry>> logs = new HashMap<>();
    private final StreakCalculator streaks = new StreakCalculator();
    private final HabitRepository repo;

    public HabitService(HabitRepository repo) { this.repo = repo; }

    public void load() throws IOException, ClassNotFoundException {
        habits.clear(); logs.clear();
        habits.putAll(repo.loadHabits());
        logs.putAll(repo.loadLogs());
    }

    public void save() throws IOException { repo.save(habits, logs); }

    public Habit addHabit(String name, String description, Frequency frequency) {
        Habit h = new Habit(name, description, frequency);
        habits.put(h.getId(), h);
        logs.put(h.getId(), new ArrayList<>());
        return h;
    }

    public boolean removeHabit(UUID id) {
        logs.remove(id);
        return habits.remove(id) != null;
    }

    public Collection<Habit> listHabits() { return habits.values(); }

    public void checkOff(UUID habitId, LocalDate date) {
        List<HabitLogEntry> l = logs.computeIfAbsent(habitId, k -> new ArrayList<>());
        boolean exists = l.stream().anyMatch(e -> e.getDate().equals(date));
        if (!exists) l.add(new HabitLogEntry(date));
    }

    public List<HabitLogEntry> getLogs(UUID habitId) {
        return logs.getOrDefault(habitId, List.of());
    }

    public int getStreak(UUID habitId, LocalDate today) {
        Habit h = habits.get(habitId);
        if (h == null) return 0;
        return streaks.compute(h.getFrequency(), logs.getOrDefault(habitId, List.of()), today);
    }
}
