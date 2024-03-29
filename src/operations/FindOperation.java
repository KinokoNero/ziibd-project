package operations;

import dict.Dictionary;
import menu.DatabaseManager;
import menu.Menu;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class FindOperation extends Operation {
    public FindOperation(String menuEntryText, Class modelClass) {
        super(menuEntryText, modelClass);
    }

    @Override
    public void execute() {
        System.out.println("Wybierz właściwość po której wyszukać:");
        Field[] searchColumn = Menu.queryUserForColumn(this.modelFields);
        Object[] searchValue = Menu.queryUserForData(searchColumn);
        ResultSet result = DatabaseManager.find(this.tableName, Dictionary.getColumnNameFromFieldName(searchColumn[0].getName()), searchValue[0]);
        Menu.printQueryResult(result);
    }
}
