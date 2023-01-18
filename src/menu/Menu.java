package menu;

import models.Client;
import models.Model;
import models.Order;
import models.Package;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Operation[] options = {
            //create methods
            new Operation("Add new client", "add", Client.class),
            new Operation("Create new package", "add", Package.class),
            new Operation("Create new order", "add", Order.class),
            //list methods
            new Operation("List clients", "list", Client.class),
            new Operation("List packages", "list", Package.class),
            new Operation("List orders", "list", Order.class),
            //search methods
            new Operation("Find clients", "find", Client.class),
            new Operation("Find packages", "find", Package.class),
            new Operation("Find orders", "find", Order.class),
            //modify methods
            new Operation("Send package", "modify", Package.class),
            new Operation("Mark order as delivered", "modify", Order.class)
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

        int chosenOption = Integer.parseInt(readUserInput()) - 1;
        options[chosenOption].choose();
    }

    //TODO: naprawić funkcjonalność poniższej funkcji
    public static void queryUserForData(Operation operation, Field[] fields) {
        try {
            String modelClassName = operation.modelClass.getName();
            Class<?> aClass = Class.forName(modelClassName);
            Constructor<?> constructor = aClass.getConstructor(String.class, String.class, String.class, String.class, String.class);

            Object[] fieldValues = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                System.out.println(String.format("Wprowadź wartość dla atrybutu '%s'", field.getName()));
                field.set(new Object(), readUserInput());
                fieldValues[i] = field.get(field);
            }
            System.out.println("DEBUG: " + fieldValues[0]);

            Object object = constructor.newInstance(fieldValues);
        }
        catch(ClassNotFoundException exception) {System.out.println("Could not find specified class!\n" + exception.getMessage());}
        catch(NoSuchMethodException exception) {System.out.println("Could not find specified method!\n" + exception.getMessage());}
        catch(InstantiationException | InvocationTargetException exception) {System.out.println("Problem occured while instantiating object!\n" + exception.getMessage());}
        catch(IllegalAccessException exception) {System.out.println("A problem occured while querying for data!\n" + exception.getMessage());}
    }
}
