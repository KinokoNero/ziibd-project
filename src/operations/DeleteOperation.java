package operations;

import menu.DatabaseManager;
import menu.Menu;

import java.lang.reflect.Field;

public class DeleteOperation extends Operation {
    public DeleteOperation(String menuEntryText, Class modelClass) {
        super(menuEntryText, modelClass);
    }

    @Override
    public void execute() {
        System.out.println("Wybierz właściwość po której wyszukać elementy do usunięcia:");
        Field[] searchColumn = Menu.queryUserForColumn(this.modelFields);
        System.out.println("Wybierz wartość właściwości - elementy o tej wartości właściwości zostaną usunięte:");
        Object[] searchValue = Menu.queryUserForData(searchColumn);
        DatabaseManager.delete(this.tableName, searchColumn[0].getName(), searchValue[0]);
    }
}
