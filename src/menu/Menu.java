package menu;

import dict.Dictionary;
import models.Client;
import models.Order;
import models.Package;
import operations.*;

import javax.management.InvalidAttributeValueException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Menu {
    public static boolean pressedExit = false;
    public static boolean error = false;

    private static final Operation[] operations = {
            //create methods
            new AddOperation("Dodaj nowego klienta", Client.class),
            new AddOperation("Stwórz nowe zamówienie", Order.class),
            new AddOperation("Stwórz nową paczkę", Package.class),
            //list methods
            new ListOperation("Wyświetl wszystkich klientów", Client.class),
            new ListOperation("Wyświetl wszystkie zamówienia", Order.class),
            new ListOperation("Wyświetl wszystkie paczki", Package.class),
            //search methods
            new FindOperation("Wyszukaj klienta", Client.class),
            new FindOperation("Wyszukaj zamówienie", Order.class),
            new FindOperation("Wyszukaj paczkę", Package.class),
            //modify methods
            new ModifyOperation("Zmień dane klienta", Client.class),
            new ModifyOperation("Zmień dane zamówienia", Order.class),
            new ModifyOperation("Zmień dane paczki", Package.class),
            //delete methods
            new DeleteOperation("Usuń klienta", Client.class),
            new DeleteOperation("Usuń zamówienie", Order.class),
            new DeleteOperation("Usuń paczkę", Package.class),
            //exit method
            new ExitOperation("Zamknij aplikację")
    };

    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        }
        catch(IOException | InterruptedException exception) {System.err.println("Problem occurred while trying to clear screen!\n" + exception.getMessage());}
    }

    public static String readUserInput(Type expectedInputType) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        validate(expectedInputType, input);
        return input;
    }

    public static void validate(Type expectedInputType, String input) {
        try {
            if (input == null || input.equals(""))
                throw new InvalidAttributeValueException();

            if (expectedInputType == int.class || expectedInputType == long.class)
                Integer.parseInt(input);
            else if (expectedInputType == double.class)
                Double.parseDouble(input);
            else if (expectedInputType == Date.class)
                LocalDate.parse(input);
        }
        catch(NumberFormatException | InvalidAttributeValueException | DateTimeParseException exception) {
            System.err.println("Invalid value type!\n" + exception.getMessage());
            error = true;
        }
    }

    public static Object[] queryUserForData(Field[] fields) {
        try {
            Object[] values = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                System.out.printf("Wprowadź wartość dla atrybutu '%s'%n", Dictionary.getDisplayNameFromFieldName(fields[i].getName()));
                values[i] = Menu.readUserInput(fields[i].getType());
                if (error) {return new Object[0];}

                if (fields[i].getType() == int.class || fields[i].getType() == double.class)
                    values[i] = NumberFormat.getInstance().parse((String) values[i]);
                if (fields[i].getType() == Date.class)
                    values[i] = LocalDate.parse((CharSequence) values[i]);
            }

            clearScreen();
            return values;
        } catch (ParseException exception) {
            System.err.println("Problem occured while parsing user input!\n" + exception.getMessage());
        }
        return new Object[0];
    }

    public static Field[] queryUserForColumn(Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            System.out.println(String.format("%d. %s", i + 1, Dictionary.getDisplayNameFromFieldName(fields[i].getName())));
        }
        int option = Integer.parseInt(readUserInput(int.class));
        if (error) {return new Field[0];}
        clearScreen();
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
        try {
            for (int i = 0; i < operations.length; i++) {
                System.out.printf("%d. %s%n", i + 1, operations[i].menuEntryText);
            }

            int chosenOption = Integer.parseInt(readUserInput(int.class)) - 1;
            clearScreen();
            operations[chosenOption].execute();
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException exception) {System.err.println("Invalid operation number!\n");}
    }
}
