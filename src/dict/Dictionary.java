package dict;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dictionary {
    private static final ArrayList<DictionaryEntry> dictionary = new ArrayList<>(List.of(
            new DictionaryEntry("id", "id", "identyfikator"),

            new DictionaryEntry("firstName", "first_name", "imię"),
            new DictionaryEntry("lastName", "last_name", "nazwisko"),
            new DictionaryEntry("phoneNumber", "phone_number", "numer telefonu"),
            new DictionaryEntry("email", "email", "email"),
            new DictionaryEntry("address", "address", "adres"),

            new DictionaryEntry("clientId", "client_id", "identyfikator klienta"),
            new DictionaryEntry("price", "price", "cena"),
            new DictionaryEntry("orderDate", "order_date", "data zamówienia"),
            new DictionaryEntry("status", "status", "status"),

            new DictionaryEntry("orderId", "order_id", "identyfikator zamówienia"),
            new DictionaryEntry("weight", "weight", "waga"),
            new DictionaryEntry("contents", "contents", "zawartość")
    ));

    public static String getColumnName(String fieldName) {

        return dictionary
                .stream()
                .filter(dictionaryEntry -> Objects.equals(dictionaryEntry.fieldName, fieldName))
                .findFirst()
                .get()
                .databaseColumnName;
    }

    public static String getDisplayName(String fieldName) {
        return dictionary
                .stream()
                .filter(dictionaryEntry -> Objects.equals(dictionaryEntry.fieldName, fieldName))
                .findFirst()
                .get()
                .displayName;
    }
}
