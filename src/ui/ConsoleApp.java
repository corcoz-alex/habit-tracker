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
        try { service.load(); System.out.println("Loaded."); } catch (Exception ignored) { }

        boolean running = true;
        while (running) {
            System.out.println("\n1) Add  2) List  3) Check  4) Streak  5) Remove  6) Save  0) Exit");
            System.out.print("> ");
            String c = IN.nextLine().trim();
            switch (c) {
                case "1" -> addHabit(service);
                case "2" -> listHabits(service);
                case "3" -> checkOff(service);
                case "4" -> showStreak(service);
                case "5" -> removeHabit(service);
                case "6" -> save(service);
                case "0" -> { save(service); running = false; }
                default -> System.out.println("?");
            }
        }
    }

    private void addHabit(HabitService svc) {
        System.out.print("Name: ");
        String name = IN.nextLine();
        System.out.print("Desc: ");
        String desc = IN.nextLine();
        System.out.print("Freq (DAILY/WEEKLY): ");
        Frequency f = Frequency.valueOf(IN.nextLine().trim().toUpperCase());
        Habit h = svc.addHabit(name, desc, f);
        System.out.println("Added " + h.getId());
    }

    private void listHabits(HabitService svc) {
        var list = svc.listHabits();
        if (list.isEmpty()) { System.out.println("Empty"); return; }
        for (Habit h : list) System.out.println(h.getId() + " | " + h.getName() + " | " + h.getFrequency());
    }

    private void checkOff(HabitService svc) {
        System.out.print("UUID: ");
        UUID id = UUID.fromString(IN.nextLine().trim());
        System.out.print("Date (YYYY-MM-DD) or empty: ");
        String s = IN.nextLine().trim();
        LocalDate d = s.isEmpty() ? LocalDate.now() : LocalDate.parse(s);
        svc.checkOff(id, d);
        System.out.println("OK");
    }

    private void showStreak(HabitService svc) {
        System.out.print("UUID: ");
        UUID id = UUID.fromString(IN.nextLine().trim());
        System.out.println("Streak = " + svc.getStreak(id, LocalDate.now()));
        System.out.println("Logs: " + svc.getLogs(id));
    }

    private void removeHabit(HabitService svc) {
        System.out.print("UUID: ");
        UUID id = UUID.fromString(IN.nextLine().trim());
        System.out.println(svc.removeHabit(id) ? "Removed" : "Not found");
    }

    private void save(HabitService svc) {
        try { svc.save(); System.out.println("Saved"); } catch (IOException e) { System.out.println("Save failed"); }
    }
}
