import models.*;
import models.Package;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Properties;

public class DatabaseManager {
    public Properties getConfigProperties() {
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
    public Connection getDatabaseConnection() {
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
        return null;
    }
    public int getNextId(String tableName) {
        try {
            int id = 0;
            String query = "select max(id) from ?";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, tableName);
            ResultSet result = preparedStatement.executeQuery();
            if (result != null) id = result.getInt(1) + 1;
            return id;
        }
        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
        return 0;
    }

    public DatabaseManager() {
        this.connection = getDatabaseConnection();
    }

    private Connection connection;

    //creates
    public void createNewClient(String firstName, String lastName, String phoneNumber, String email, String address) {
        try {
            int id = getNextId("clients");
            String query = "insert into clients values(?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, address);

            preparedStatement.execute();
        }
        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
    }
    public void createNewPackage(double weight, String contents) {
        try {
            int id = getNextId("packages");
            String query = "insert into packages values(?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.setDouble(2, weight);
            preparedStatement.setString(3, contents);

            preparedStatement.execute();
        }
        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
    }
    public void createNewOrder(double price, String address) {
        try {
            int id = getNextId("orders");
            String query = "insert into orders values(?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, address);
            preparedStatement.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
            preparedStatement.setString(5, "New");

            preparedStatement.execute();
        }
        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
    }
}
