# Habit Tracker (Java, IntelliJ)

A simple console-based habit tracker app in Java

**Packages**
- `domain`: models (`Habit`, `HabitLogEntry`, `Frequency`)
- `repository`: contracts + file implementation
- `service`: use-cases and streak logic
- `ui`: console interface

**Run**
1) Open in IntelliJ
2) Run `Main` (it starts the UI)
3) Data is saved to `./data/habits.dat`

**Features**
- Add/list/remove habits
- Check off by date or today
- Show current streak
