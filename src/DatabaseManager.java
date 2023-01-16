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

    public DatabaseManager() {
        this.connection = getDatabaseConnection();
    }

    private Connection connection;

    public void createNewPackage(double price, String address) { //TODO<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery("count * from orders");
            int id = result.getInt(1);

            statement.executeQuery(
                    String.format(
                            "insert into orders values(%d, %.2f, %s, %t, %s",
                            id,
                            price,
                            address,
                            new java.sql.Date(Calendar.getInstance().getTimeInMillis()),
                            "Nowe"
                    )
            );
        }
        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
    }

    public void createNewOrder(double price, String address) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery("count * from orders");
            int id = result.getInt(1);

            statement.executeQuery(
                    String.format(
                            "insert into orders values(%d, %.2f, %s, %t, %s",
                            id,
                            price,
                            address,
                            new java.sql.Date(Calendar.getInstance().getTimeInMillis()),
                            "Nowe"
                    )
            );
        }
        catch(SQLException exception) {System.out.println("Could not complete database operation!");}
    }
}
