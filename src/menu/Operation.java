package menu;

import models.Model;

public abstract class Operation {
    public Operation(String menuEntryText, String type, String table) {
        this.menuEntryText = menuEntryText;
        this.type = type;
        this.table = table;
    }

    public String menuEntryText;
    public String type;
    public String table;

    public abstract void addRecord(Model model);
}
