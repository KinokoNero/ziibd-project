package menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static Properties getConfigProperties() {
        try {
            final String CONFIG_FILE_PATH;
            if (System.getProperty("os.name").contains("Windows"))
                CONFIG_FILE_PATH = System.getProperty("user.dir") + "\\resources\\config.properties";
            else
                CONFIG_FILE_PATH = System.getProperty("user.dir") + "/resources/config.properties";

            FileInputStream propertiesInputStream = new FileInputStream(CONFIG_FILE_PATH);
            Properties properties = new Properties();
            properties.load(propertiesInputStream);
            return properties;
        }
        catch(FileNotFoundException exception) {System.err.println("Config file not found!\n" + exception.getMessage());}
        catch(IOException exception) {System.err.println("Could not load config file!\n" + exception.getMessage());}
        return null;
    }
    public static void init() {
        Properties properties = getConfigProperties();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            assert properties != null;
            connection = DriverManager.getConnection(
                    properties.getProperty("DB_URL"),
                    properties.getProperty("DB_USER"),
                    properties.getProperty("DB_PASSWORD")
            );
        }
        catch(ClassNotFoundException exception) {System.err.println("Oracle JDBC driver not found!\n" + exception.getMessage());}
        catch(SQLException exception) {System.err.println("Could not connect to database!\n" + exception.getMessage());}
        catch(NullPointerException exception) {System.err.println("Could not fetch database connection properties!\n" + exception.getMessage());}
    }
    public static int getNextId(String tableName) {
        try {
            int id = 0;
            String query = String.format("select max(id) from %s", tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            id = result.getInt(1) + 1;
            return id;
        }
        catch(SQLException exception) {System.err.println("Could not complete database operation!");}
        return 0;
    }

    private static Connection connection;

    public static void insert(String tableName, Object[] data) {
        try {
            int id = getNextId(tableName);

            //build query
            StringBuilder query = new StringBuilder(String.format("insert into %s values(%d,", tableName, id));
            for (Object singleData : data) {
                if (singleData.getClass() == String.class || singleData.getClass() == java.util.Date.class)
                    query.append(String.format("'%s',", singleData));
                else
                    query.append(String.format("%s,", singleData));
            }
            query.deleteCharAt(query.length() - 1); //shave off last comma
            query.append(")"); //close query value list

            //execute query
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            preparedStatement.execute();
        }
        catch(SQLException exception) {System.err.println("Could not complete database operation!\n" + exception.getMessage());}
    }

    public static String list(String tableName) {
        try {
            String query = String.format("select * from %s", tableName);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery().toString();
        }
        catch(SQLException exception) {System.err.println("Could not complete database operation!\n" + exception.getMessage());}
        return null;
    }
}
