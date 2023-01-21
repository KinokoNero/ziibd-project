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
        Field[] searchColumn = Menu.queryUserForColumn(this.modelFields);
        Object[] searchValue = Menu.queryUserForData(searchColumn);
        ResultSet result = DatabaseManager.find(this.tableName, Dictionary.getColumnName(searchColumn[0].getName()), searchValue[0]);
        DatabaseManager.printQueryResult(result);
    }
}
