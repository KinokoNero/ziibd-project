import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import models.*;

public class Main {


    public void displayMenu() {
        System.out.println("Wybierz opcję:");
        System.out.println("1. Stwórz nowe zamówienie");
        System.out.println("2. Wyszukaj zamówienie");
        System.out.println("3. Wyślij paczkę");
    }

    public static void main(String[] args) {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from employees");
        System.out.println(result.getInt(1)+" "+result.getString(2)+" "+result.getString(3));
    }
}