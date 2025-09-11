package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Habit implements Serializable {
    private final UUID id;
    private String name;
    private String description;
    private Frequency frequency;
    private final LocalDate createdOn;

    public Habit(String name, String description, Frequency frequency) {
        this(UUID.randomUUID(), name, description, frequency, LocalDate.now());
    }

    public Habit(UUID id, String name, String description, Frequency frequency, LocalDate createdOn) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.description = description == null ? "" : description;
        this.frequency = Objects.requireNonNull(frequency);
        this.createdOn = Objects.requireNonNull(createdOn);
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Frequency getFrequency() { return frequency; }
    public LocalDate getCreatedOn() { return createdOn; }

    public void rename(String n) { this.name = Objects.requireNonNull(n); }
    public void changeDescription(String d) { this.description = d == null ? "" : d; }
    public void changeFrequency(Frequency f) { this.frequency = Objects.requireNonNull(f); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habit h)) return false;
        return id.equals(h.id);
    }
    @Override public int hashCode() { return id.hashCode(); }

    @Override public String toString() {
        return "Habit{" + id + ", '" + name + "', " + frequency + "}";
    }
}
