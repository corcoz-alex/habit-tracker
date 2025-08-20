package service;

import domain.Frequency;
import domain.HabitLogEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreakCalculator {

    public int compute(Frequency frequency, List<HabitLogEntry> logs, LocalDate today) {
        if (logs == null || logs.isEmpty()) return 0;
        if (frequency == Frequency.DAILY) return daily(logs, today);
        return weekly(logs, today);
    }

    private int daily(List<HabitLogEntry> logs, LocalDate today) {
        Set<LocalDate> days = logs.stream().map(HabitLogEntry::getDate).collect(Collectors.toSet());
        int s = 0;
        LocalDate d = today;
        while (days.contains(d)) { s++; d = d.minusDays(1); }
        return s;
    }

    private int weekly(List<HabitLogEntry> logs, LocalDate today) {
        Set<String> weeks = logs.stream()
                .map(e -> e.getDate().getYear() + "-W" + e.getDate().get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR))
                .collect(Collectors.toSet());
        int s = 0;
        LocalDate c = today;
        while (true) {
            String key = c.getYear() + "-W" + c.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            if (!weeks.contains(key)) break;
            s++; c = c.minusWeeks(1);
        }
        return s;
    }
}
