package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import il.cshaifasweng.OCSFMediatorExample.entities.models.MenuClient;

import java.io.Serializable;

public class SubmitionRequestToNetworkManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private final MenuClient menuToSubmit;

    public SubmitionRequestToNetworkManager(MenuClient menuToSubmit) {
        this.menuToSubmit = menuToSubmit;
    }
    public MenuClient getMenuToSave() {
        return menuToSubmit;
    }
}
