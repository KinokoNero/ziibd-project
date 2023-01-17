package menu;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private static Operation[] options = {
            //create methods
            new AddOperation("Add new client", "add", "clients"),
            new AddOperation("Create new package", "add", "packages"),
            new AddOperation("Create new order", "add", "orders"),
            //list methods
            new ListOperation("List clients", "list", "clients"),
            new ListOperation("List packages", "list", "packages"),
            new ListOperation("List orders", "list", "orders"),
            //search methods
            new FindOperation("Find clients", "find", "clients"),
            new FindOperation("Find packages", "find", "packages"),
            new FindOperation("Find orders", "find", "orders"),
            //modify methods
            new ModifyOperation("Send package", "modify", "packages"),
            new ModifyOperation("Mark order as delivered", "modify", "orders")
    };

    public static void clearScreen() { //TODO: fix screen clearing
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "cls");
                Process process = processBuilder.inheritIO().start();
                process.waitFor();
            }
            else if (os.contains("Linux")) {
                ProcessBuilder processBuilder = new ProcessBuilder("bash", "clear");
                Process process = processBuilder.inheritIO().start();
                process.waitFor();
            }
        }
        catch(IOException | InterruptedException exception) {System.out.println("Problem occurred while trying to clear screen!\n" + exception.getMessage());}
    }

    public static String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void displayMainMenu() {
        for(int i = 0; i < options.length; i++) {
            System.out.println(String.format("%d. %s", i + 1, options[i].menuEntryText));
        }

        readUserInput();
    }
}
