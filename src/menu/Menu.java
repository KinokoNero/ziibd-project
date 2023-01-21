package menu;

import dict.Dictionary;
import models.Client;
import models.Order;
import models.Package;
import operations.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Scanner;

public class Menu {
    public static boolean pressedExit = false;

    private static final Operation[] operations = {
            //create methods
            new AddOperation("Dodaj nowego klienta", Client.class),
            new AddOperation("Stwórz nowe zamówienie", Order.class),
            new AddOperation("Stwórz nową paczkę", Package.class),

            //list methods
            new ListOperation("Wyświetl wszystkich klientów", Client.class),
            new ListOperation("Wyświetl wszystkie zamówienia", Order.class), //TODO: fix list operations
            new ListOperation("Wyświetl wszystkie paczki", Package.class),
            //search methods
            new FindOperation("Wyszukaj klienta", Client.class),
            new FindOperation("Wyszukaj paczkę", Package.class),
            new FindOperation("Wyszukaj zamówienie", Order.class),
//            //modify methods
//            new ModifyOperation("Zmień status zamówienia", Order.class)
            //delete methods
            new DeleteOperation("Usuń klienta", Client.class),
            new DeleteOperation("Usuń zamówienie", Order.class),
            new DeleteOperation("Usuń paczkę", Package.class),

            new ExitOperation("Zamknij aplikację")
    };

    //TODO: fix screen clearing
    public static void clearScreen() {
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
        catch(IOException | InterruptedException exception) {System.err.println("Problem occurred while trying to clear screen!\n" + exception.getMessage());}
    }

    public static String readUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static Object[] queryUserForData(Field[] fields) {
        try {
            Object[] values = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                System.out.printf("Wprowadź wartość dla atrybutu '%s'%n", Dictionary.getDisplayName(fields[i].getName()));
                values[i] = Menu.readUserInput();

                //parse if number
                if (fields[i].getType() == int.class || fields[i].getType() == double.class) {
                    values[i] = NumberFormat.getInstance().parse((String) values[i]);
                }
            }
            return values;
        }
        catch(ParseException exception) {System.err.println("Problem occured while parsing user input!\n" + exception.getMessage());}
        return new Object[0];
    }

    public static Field[] queryUserForColumn(Field[] fields) {
        System.out.println("Wybierz właściwość po której wyszukać:");
        for (int i = 0; i < fields.length; i++) {
            System.out.println(String.format("%d. %s", i, Dictionary.getDisplayName(fields[i].getName())));
        }
        int option = Integer.parseInt(readUserInput());
        return new Field[] {fields[option]};
    }

    public static void displayMainMenu() {
        for(int i = 0; i < operations.length; i++) {
            System.out.printf("%d. %s%n", i + 1, operations[i].menuEntryText);
        }

        int chosenOption = Integer.parseInt(readUserInput()) - 1;
        operations[chosenOption].execute();
    }
}
