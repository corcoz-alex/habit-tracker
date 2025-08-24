package ui;

import domain.Frequency;
import domain.Habit;
import repository.FileHabitRepository;
import service.HabitService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleApp {

    private static final Scanner IN = new Scanner(System.in);

    public void run() {
        System.out.println("Habit Tracker");
        var service = new HabitService(new FileHabitRepository("data"));
        try {
            service.load();
            System.out.println("Loaded previous data.");
        } catch (Exception e) {
            System.out.println("No saved data found. Starting clean.");
        }

        boolean keepRunning = true;
        while (keepRunning) {
            printMenu();
            String choice = IN.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addHabit(service);
                    case "2" -> listHabits(service);
                    case "3" -> checkOff(service);
                    case "4" -> showStreak(service);
                    case "5" -> removeHabit(service);
                    case "6" -> save(service);
                    case "0" -> { save(service); keepRunning = false; }
                    default -> System.out.println("Unknown option. Try again.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Input problem: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Something went wrong: " + ex.getMessage());
            }
        }

        System.out.println("Goodbye.");
    }

    private void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1) Add habit");
        System.out.println("2) List habits");
        System.out.println("3) Check off habit (for a date)");
        System.out.println("4) Show streak for a habit");
        System.out.println("5) Remove habit");
        System.out.println("6) Save");
        System.out.println("0) Save & Exit");
        System.out.print("> ");
    }

    private void addHabit(HabitService svc) {
        System.out.print("Name: ");
        String name = IN.nextLine().trim();
        if (name.isEmpty()) throw new IllegalArgumentException("Name can't be empty.");

        System.out.print("Description (optional): ");
        String desc = IN.nextLine().trim();

        System.out.print("Frequency (DAILY/WEEKLY): ");
        Frequency freq = Frequency.valueOf(IN.nextLine().trim().toUpperCase());

        Habit h = svc.addHabit(name, desc, freq);
        System.out.println("Added: " + h.getId() + " | " + h.getName() + " [" + h.getFrequency() + "]");
    }

    private void listHabits(HabitService svc) {
        var list = svc.listHabits();
        if (list.isEmpty()) {
            System.out.println("No habits yet.");
            return;
        }
        for (Habit h : list) {
            System.out.println(" - " + h.getId() + " | " + h.getName() + " [" + h.getFrequency() + "] created " + h.getCreatedOn());
        }
    }

    private void checkOff(HabitService svc) {
        System.out.print("Habit UUID: ");
        UUID id = UUID.fromString(IN.nextLine().trim());

        System.out.print("Date (YYYY-MM-DD) or empty for today: ");
        String s = IN.nextLine().trim();
        LocalDate date = s.isEmpty() ? LocalDate.now() : LocalDate.parse(s);

        svc.checkOff(id, date);
        System.out.println("Marked " + date + ".");
    }

    private void showStreak(HabitService svc) {
        System.out.print("Habit UUID: ");
        UUID id = UUID.fromString(IN.nextLine().trim());
        int streak = svc.getStreak(id, LocalDate.now());
        System.out.println("Current streak: " + streak);
        System.out.println("Logs: " + svc.getLogs(id));
    }

    private void removeHabit(HabitService svc) {
        System.out.print("Habit UUID: ");
        UUID id = UUID.fromString(IN.nextLine().trim());
        boolean removed = svc.removeHabit(id);
        System.out.println(removed ? "Removed." : "Not found.");
    }

    private void save(HabitService svc) throws IOException {
        svc.save();
        System.out.println("Saved.");
    }
}
