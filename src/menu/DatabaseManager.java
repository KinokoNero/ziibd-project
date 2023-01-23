package menu;

import dict.Dictionary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
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

    public static ResultSet executeQuery(String query) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        }
        catch(SQLException exception) {System.err.println("Could not complete database operation!\n" + exception.getMessage());}
        return null;
    }

    public static void insert(String tableName, String[] columnNames, Object[] data) {
        int id = getNextId(tableName);

        //build query
        StringBuilder query = new StringBuilder(String.format("insert into %s(id,", tableName));
        //build column list
        for (String columnName : columnNames) {
            query.append(String.format("%s,", columnName));
        }
        query.deleteCharAt(query.length() - 1); //shave off last comma
        query.append(")"); //close query column list

        query.append(String.format(" values(%d,", id));

        for (int i = 0; i < data.length; i++) {
            if (columnNames[i].equals("order_date"))
                query.append(String.format("to_date('%s', 'yyyy-mm-dd'),", new Date(Calendar.getInstance().getTimeInMillis())));
            if (data[i].getClass() == String.class)
                query.append(String.format("'%s',", data[i]));
            else
                query.append(String.format("%s,", data[i]));
        }

        query.deleteCharAt(query.length() - 1); //shave off last comma
        query.append(")"); //close query value list

        executeQuery(query.toString());
    }

    public static ResultSet list(String tableName) {
        String query = String.format("select * from %s order by id", tableName);
        return executeQuery(query);
    }

    public static void delete(String tableName, String searchColumn, Object searchValue) {
        StringBuilder query = new StringBuilder(String.format("delete from %s where %s=", tableName, Dictionary.getColumnNameFromFieldName(searchColumn)));

        if (searchValue.getClass() == Long.class || searchValue.getClass() == Double.class)
            query.append(searchValue);
        else if (searchValue.getClass() == LocalDate.class)
            query.append(String.format("to_date('%s', 'yyyy-mm-dd')", searchValue));
        else
            query.append(String.format("'%s'", searchValue));

        executeQuery(query.toString());
    }

    public static ResultSet find(String tableName, String searchColumn, Object searchValue) {
        StringBuilder query = new StringBuilder(String.format("select * from %s where %s=", tableName, searchColumn));
        if (searchValue.getClass() == Long.class || searchValue.getClass() == Double.class)
            query.append(searchValue);
        else if (searchValue.getClass() == String.class)
            query.append("'" + searchValue + "'");

        query.append(" order by id");

        return executeQuery(query.toString());
    }

    public static void modify(String tableName, String searchColumn, Object searchValue, String setColumn, Object newValue) {
        StringBuilder query = new StringBuilder(String.format("update %s set %s=", tableName, setColumn));
        if (newValue.getClass() == String.class)
            query.append(String.format("'%s' ", newValue));
        else if (newValue.getClass() == Double.class || newValue.getClass() == Long.class)
            query.append(String.format("%d ", newValue));
        else if (newValue.getClass() == LocalDate.class)
            query.append(String.format("to_date('%s', 'yyyy-mm-dd')", newValue));

        query.append(String.format(" where %s=", searchColumn));
        if (searchValue.getClass() == String.class)
            query.append(String.format("'%s' ", searchValue));
        else if (searchValue.getClass() == Double.class || searchValue.getClass() == Long.class)
            query.append(String.format("%d ", searchValue));

        executeQuery(query.toString());
    }
}
