package repository;

import domain.Habit;
import domain.HabitLogEntry;
import repository.HabitRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileHabitRepository implements HabitRepository {

    public record Snapshot(Map<UUID, Habit> habits,
                           Map<UUID, List<HabitLogEntry>> logs) implements Serializable { }

    private final Path dataDir;
    private final Path filePath;

    public FileHabitRepository(String dir) {
        this.dataDir = Path.of(dir);
        this.filePath = dataDir.resolve("habits.dat");
    }

    @Override
    public void save(Map<UUID, Habit> habits, Map<UUID, List<HabitLogEntry>> logs) throws IOException {
        if (!Files.exists(dataDir)) Files.createDirectories(dataDir);
        try (var oos = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(filePath)))) {
            oos.writeObject(new Snapshot(new LinkedHashMap<>(habits), new HashMap<>(logs)));
        }
    }

    @Override
    public Map<UUID, Habit> loadHabits() throws IOException, ClassNotFoundException {
        Snapshot s = read();
        return s == null ? new LinkedHashMap<>() : s.habits();
    }

    @Override
    public Map<UUID, List<HabitLogEntry>> loadLogs() throws IOException, ClassNotFoundException {
        Snapshot s = read();
        return s == null ? new HashMap<>() : s.logs();
    }

    private Snapshot read() throws IOException, ClassNotFoundException {
        if (!Files.exists(filePath)) return null;
        try (var ois = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(filePath)))) {
            return (Snapshot) ois.readObject();
        }
    }
}
