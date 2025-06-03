package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuClient;

import java.io.Serializable;


public class SaveMenuChangesRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private final MenuClient oldMenu;
    private final MenuClient newMenu;

    public SaveMenuChangesRequest(MenuClient oldMenu, MenuClient newMenu) {
        this.oldMenu = oldMenu;
        this.newMenu = newMenu;
    }
    public MenuClient getOldMenu() {
        return oldMenu;
    }

    public MenuClient getNewMenu() {
        return newMenu;
    }
}
