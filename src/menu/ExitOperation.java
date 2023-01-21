package menu;

import models.Model;

public class ExitOperation extends Operation {
    public ExitOperation(String menuEntryText) {
        super(menuEntryText);
    }

    @Override
    public void execute() {
        Menu.pressedExit = true;
    }
}
