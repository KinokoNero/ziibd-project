package menu;

public class ListOperation extends Operation {
    public ListOperation(String menuEntryText, Class modelClass) {
        super(menuEntryText, modelClass);
    }

    @Override
    public void execute() {
        System.out.println(DatabaseManager.list(this.tableName));
    }
}
