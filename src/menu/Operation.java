package menu;

import models.Model;

public class Operation {
    public Operation(String menuEntryText, String type, Class modelClass) {
        this.menuEntryText = menuEntryText;
        this.type = type;
        //this.table = table;
        this.modelClass = modelClass;
        this.tableName = modelClass.getName() + "s";
    }

    public String menuEntryText;
    public String type;
    public String tableName;
    public Class modelClass;

    public void choose() {
        switch(this.type) {
            case "add":
                Menu.queryUserForData(this, modelClass.getFields());
                break;

            case "list":
                break;

            case "find":
                break;

            case "modify":
                break;
        }
    }

    public void addRecord(Model model) {
        DatabaseManager.insert(model);
    }
    public String listRecords() {
        return DatabaseManager.list(this.tableName);
    }
    public Object findRecord(Model model) {
        //DatabaseManager.find(model);
        return null;
    }
    public void modifyRecord(int id, Model updatedModel) {
        //DatabaseManager.modify(id, updatedModel);
    }
}
