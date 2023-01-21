package operations;

import menu.DatabaseManager;
import menu.Menu;

public class ListOperation extends Operation {
    public ListOperation(String menuEntryText, Class modelClass) {
        super(menuEntryText, modelClass);
    }

    @Override
    public void execute() {
        Menu.printQueryResult(DatabaseManager.list(this.tableName));
    }
}
