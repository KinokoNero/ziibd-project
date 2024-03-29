package operations;

import dict.Dictionary;
import models.Model;

import java.lang.reflect.Field;

public abstract class Operation {
    public Operation(String menuEntryText, Class<Model> modelClass) {
        this.menuEntryText = menuEntryText;
        this.modelClass = modelClass;
        this.modelFields = modelClass.getFields();
        this.tableName = modelClass.getSimpleName() + "s";
    }
    public Operation(String menuEntryText) {
        this.menuEntryText = menuEntryText;
    }

    public abstract void execute();
    public String menuEntryText;
    public Class<Model> modelClass;
    public Field[] modelFields;
    public String tableName;

    public static String[] translateFieldToColumns(Field[] fields) {
        String[] columns = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            columns[i] = Dictionary.getColumnNameFromFieldName(fields[i].getName());
        }
        return columns;
    }
}
