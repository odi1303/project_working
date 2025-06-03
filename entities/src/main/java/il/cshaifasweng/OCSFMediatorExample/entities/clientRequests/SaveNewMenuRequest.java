package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuClient;

import java.io.Serializable;

public class SaveNewMenuRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private final MenuClient menuToSave;

    public SaveNewMenuRequest(MenuClient menuToSave) {
        this.menuToSave = menuToSave;
    }
    public MenuClient getMenuToSave() {
        return menuToSave;
    }
}
