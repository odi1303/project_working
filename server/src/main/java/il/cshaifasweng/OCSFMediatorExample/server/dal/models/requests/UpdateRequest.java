package il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("UPDATE")
public class UpdateRequest extends Request
{
    @Column(name="menu_item_id", nullable = false)
    public Long menuItemId;

    @Column(name="menu_item_description")
    public String menuItemDescription;

    @Column(name="menu_item_price")
    public Long menuItemPrice;
    @Id
    public Long id;

    public UpdateRequest() { }

    public Long getMenuItem() {
        return menuItemId;
    }

    public String getMenuItemDescription() {
        return menuItemDescription;
    }

    public Long getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

