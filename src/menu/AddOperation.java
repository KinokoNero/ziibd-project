package menu;

import models.Model;

import java.lang.reflect.Field;

public class AddOperation extends Operation {
    public AddOperation(String menuEntryText, Class modelClass) {
        super(menuEntryText, modelClass);
    }

    @Override
    public void execute() {
        Object[] data = Menu.queryUserForData(this.modelClass.getFields());
        DatabaseManager.insert(this.tableName, data);
    }
}
