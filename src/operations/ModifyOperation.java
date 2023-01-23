package operations;

import dict.Dictionary;
import menu.DatabaseManager;
import menu.Menu;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ModifyOperation extends Operation {
    public ModifyOperation(String menuEntryText, Class modelClass) {
        super(menuEntryText, modelClass);
    }

    @Override
    public void execute() {
        Field[] setFields = Arrays.stream(this.modelFields).filter(field -> !field.getName().equals("id")).toArray(Field[]::new);
        System.out.println("Wybierz właściwość po której wyszukać element do zmiany:");
        Field[] searchColumn = Menu.queryUserForColumn(this.modelFields);
        Object[] searchValue = Menu.queryUserForData(searchColumn);
        if (Menu.error) return;
        System.out.println("Wybierz właściwość którą chcesz zmienić:");
        Field[] setColumn = Menu.queryUserForColumn(setFields);
        Object[] newValue = Menu.queryUserForData(setColumn);
        if (Menu.error) return;
        DatabaseManager.modify(this.tableName, Dictionary.getColumnNameFromFieldName(searchColumn[0].getName()), searchValue[0], Dictionary.getColumnNameFromFieldName(setColumn[0].getName()), newValue[0]);
    }
}
