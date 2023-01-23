import menu.*;

public class Main {
    public static void main(String[] args) {
        DatabaseManager.init();

        while (!Menu.pressedExit) {
            Menu.displayMainMenu();
            Menu.error = false;
        }
    }
}