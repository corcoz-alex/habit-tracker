package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Habit implements Serializable {
    private final UUID id;
    private String name;
    private String description;
    private Frequency frequency;
    private final LocalDate createdOn;

    public Habit(String name, String description, Frequency frequency) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.createdOn = LocalDate.now();
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Frequency getFrequency() { return frequency; }
    public LocalDate getCreatedOn() { return createdOn; }

    public void rename(String n) { this.name = n; }
    public void changeDescription(String d) { this.description = d; }
    public void changeFrequency(Frequency f) { this.frequency = f; }

    @Override
    public String toString() {
        return id + " " + name + " " + frequency;
    }
}
