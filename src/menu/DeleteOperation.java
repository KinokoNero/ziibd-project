package menu;

import models.Model;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DeleteOperation extends Operation {
    public DeleteOperation(String menuEntryText, Class modelClass) {
        super(menuEntryText, modelClass);
    }

    @Override
    public void execute() {
        Field[] fields = Arrays.stream(this.modelFields).filter(field -> field.getName().equals("id")).toArray(Field[]::new);
        Object[] data = Menu.queryUserForData(fields);
        DatabaseManager.delete(this.tableName, data);
    }
}
