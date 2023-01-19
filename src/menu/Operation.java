package menu;

import models.Model;

public abstract class Operation {
    public Operation(String menuEntryText, Class<Model> modelClass) {
        this.menuEntryText = menuEntryText;
        this.modelClass = modelClass;
        this.tableName = modelClass.getSimpleName() + "s";
    }

    public abstract void execute();
    public String menuEntryText;
    //public String type;
    public Class<Model> modelClass;
    public String tableName;
}
