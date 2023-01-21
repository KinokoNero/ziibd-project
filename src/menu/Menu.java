package menu;

import dict.Dictionary;
import models.Client;
import models.Order;
import models.Package;
import operations.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
            new FindOperation("Wyszukaj zamówienie", Order.class),
            new FindOperation("Wyszukaj paczkę", Package.class),
            //modify methods
//            new ModifyOperation("Zmień status zamówienia", Order.class)
            //delete methods
            new DeleteOperation("Usuń klienta", Client.class),
            new DeleteOperation("Usuń zamówienie", Order.class),
            new DeleteOperation("Usuń paczkę", Package.class),
            //exit method
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

    public static void validate(String input) {
        //TODO: add body for validating user input
    }

    public static Object[] queryUserForData(Field[] fields) {
        try {
            Object[] values = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                System.out.printf("Wprowadź wartość dla atrybutu '%s'%n", Dictionary.getDisplayNameFromFieldName(fields[i].getName()));
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
            System.out.println(String.format("%d. %s", i + 1, Dictionary.getDisplayNameFromFieldName(fields[i].getName())));
        }
        int option = Integer.parseInt(readUserInput());
        return new Field[] {fields[option - 1]};
    }

    public static void printQueryResult(ResultSet queryResult) {
        try {
            ResultSetMetaData resultSetMetaData = queryResult.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();

            while(queryResult.next()) {
                for (int i = 1; i < columnsNumber + 1; i++) {
                    String columnName = Dictionary.getDisplayNameFromColumnName(resultSetMetaData.getColumnName(i).toLowerCase());
                    String value = queryResult.getString(i);

                    if (value.contains("00:00:00"))
                        value = value.substring(0, 10);

                    System.out.printf("%s: %s%n", columnName, value);
                }
                System.out.println();
            }
        }
        catch(SQLException exception) {System.err.println("Could not print query result!\n" + exception.getMessage());}
    }

    public static void displayMainMenu() {
        for(int i = 0; i < operations.length; i++) {
            System.out.printf("%d. %s%n", i + 1, operations[i].menuEntryText);
        }

        int chosenOption = Integer.parseInt(readUserInput()) - 1;
        operations[chosenOption].execute();
    }
}
