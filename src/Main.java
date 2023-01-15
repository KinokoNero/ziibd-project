import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import models;

public class Main {
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

    public static void main(String[] args) {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from employees");
        System.out.println(result.getInt(1)+" "+result.getString(2)+" "+result.getString(3));
    }
}