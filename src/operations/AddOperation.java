package operations;

import menu.DatabaseManager;
import menu.Menu;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AddOperation extends Operation {
    public AddOperation(String menuEntryText, Class modelClass) {
        super(menuEntryText, modelClass);
    }

    @Override
    public void execute() {
        Field[] insertFields = Arrays.stream(this.modelFields).filter(field -> !field.getName().equals("id")).toArray(Field[]::new);
        Field[] queryFields = Arrays.stream(insertFields).filter(field -> !field.getName().equals("id") && !field.getName().equals("orderDate")).toArray(Field[]::new);
        Object[] data = Menu.queryUserForData(queryFields);
        DatabaseManager.insert(this.tableName, translateFieldToColumns(insertFields), data);
    }
}
