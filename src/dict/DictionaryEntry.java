package dict;

public class DictionaryEntry {
    public DictionaryEntry(String fieldName, String databaseColumnName, String displayName) {
        this.fieldName = fieldName;
        this.databaseColumnName = databaseColumnName;
        this.displayName = displayName;
    }

    public String fieldName; //name of field to be translated from
    public String databaseColumnName; //name of table column in the database
    public String displayName; //name displayed for querying user
}
