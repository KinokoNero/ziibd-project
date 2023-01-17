package models;

import menu.DatabaseManager;

public abstract class Model {
    public Model() {
        this.id = DatabaseManager.getNextId(this.getClass().getName() + "s");
    }
    public int id;
}
