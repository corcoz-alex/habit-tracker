import ui.ConsoleApp;

public class Main {
    public static void main(String[] args) {
        try {
            new ConsoleApp().run();
        } catch (Throwable t) {
            System.out.println("Fatal error: " + t.getMessage());
        }
    }
}
