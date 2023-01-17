package menu;

import models.*;
import models.Package;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Calendar;
import java.util.Properties;

public class DatabaseManager {
    private Properties getConfigProperties() {
        try {
            final String CONFIG_FILE_PATH = "config.properties";
            FileInputStream propertiesInputStream = new FileInputStream(CONFIG_FILE_PATH);
            Properties properties = new Properties();
            properties.load(propertiesInputStream);
            return properties;
        }
        catch(FileNotFoundException exception) {System.out.println("Config file not found!\n" + exception.getMessage());}
        catch(IOException exception) {System.out.println("Could not load config file!\n" + exception.getMessage());}
        return null;
    }
    private Connection getDatabaseConnection() {
        Properties properties = getConfigProperties();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(
                    properties.getProperty("DB_URL"),
                    properties.getProperty("DB_USER"),
                    properties.getProperty("DB_PASSWORD")
            );
        }
        catch(ClassNotFoundException exception) {System.out.println("Oracle JDBC driver not found!\n" + exception.getMessage());}
        catch(SQLException exception) {System.out.println("Could not connect to database!\n" + exception.getMessage());}
        catch(NullPointerException exception) {System.out.println("Could not fetch database connection properties!\n" + exception.getMessage());}
        return null;
    }
    public static int getNextId(String tableName) {
        try {
            int id = 0;
            String query = "select max(id) from ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tableName);
            ResultSet result = preparedStatement.executeQuery();
            if (result != null) id = result.getInt(1) + 1;
            return id;
        }
        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
        return 0;
    }

    public DatabaseManager() {
        connection = getDatabaseConnection();
    }
    private static Connection connection;

    //inserts
    public static void insert(Model model) {
        try {
            String tableName = model.getClass().getName() + "s";
            int id = getNextId(tableName);

            //build query
            Field[] fields = model.getClass().getFields();
            String query = String.format("insert into %s (", tableName);
            for (Field field:fields) { //add column list to query
                query += String.format("%s, ", field.getName());
            }
            query = query.substring(0, query.length() - 2) + ") values("; //close query column list and open values list
            for (Field field:fields) { //add values list to query
                query += field.get(model).toString() + ", ";
            }
            query = query.substring(0, query.length() - 2) + ");"; //shave off last comma and close values list

            //execute query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
        catch(SQLException exception) {System.out.println("Could not complete database operation!\n" + exception.getMessage());}
        catch(IllegalAccessException exception) {System.out.println("Could not get object's field values!\n" + exception.getMessage());}
    }

    //TODO: create more methods for operations (list, find, modify, etc.)
//    public static void createNewClient(String firstName, String lastName, String phoneNumber, String email, String address) {
//        try {
//            int id = getNextId("clients");
//            String query = "insert into clients values(?, ?, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//            preparedStatement.setInt(1, id);
//            preparedStatement.setString(2, firstName);
//            preparedStatement.setString(3, lastName);
//            preparedStatement.setString(4, phoneNumber);
//            preparedStatement.setString(5, email);
//            preparedStatement.setString(6, address);
//
//            preparedStatement.execute();
//        }
//        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
//    }
//    public static void createNewPackage(double weight, String contents) {
//        try {
//            int id = getNextId("packages");
//            String query = "insert into packages values(?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//            preparedStatement.setInt(1, id);
//            preparedStatement.setDouble(2, weight);
//            preparedStatement.setString(3, contents);
//
//            preparedStatement.execute();
//        }
//        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
//    }
//    public void createNewOrder(double price, String address) {
//        try {
//            int id = getNextId("orders");
//            String query = "insert into orders values(?, ?, ?, ?, ?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//            preparedStatement.setInt(1, id);
//            preparedStatement.setDouble(2, price);
//            preparedStatement.setString(3, address);
//            preparedStatement.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
//            preparedStatement.setString(5, "New");
//
//            preparedStatement.execute();
//        }
//        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
//    }
}
