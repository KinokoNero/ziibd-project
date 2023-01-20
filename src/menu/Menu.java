package menu;

import dict.Dictionary;
import models.Client;
import models.Order;
import models.Package;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Scanner;

public class Menu {
    private static final Operation[] options = {
            //create methods
            new AddOperation("Add new client", Client.class),
            new AddOperation("Create new order", Order.class),
            new AddOperation("Create new package", Package.class),

            //list methods
            new ListOperation("List clients", Client.class),
            new ListOperation("List packages", Package.class),
            new ListOperation("List orders", Order.class),
            //search methods
//            new FindOperation("Find clients", Client.class),
//            new FindOperation("Find packages", Package.class),
//            new FindOperation("Find orders", Order.class),
//            //modify methods
//            new ModifyOperation("Send package", Package.class),
//            new ModifyOperation("Mark order as delivered", Order.class)
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
                if (fields[i].getType() == Date.class) {
                    values[i] = new Date(Calendar.getInstance().getTimeInMillis());
                    continue;
                }

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

    public static void displayMainMenu() {
        for(int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i].menuEntryText);
        }

        int chosenOption = Integer.parseInt(readUserInput()) - 1;
        options[chosenOption].execute();
    }
}
