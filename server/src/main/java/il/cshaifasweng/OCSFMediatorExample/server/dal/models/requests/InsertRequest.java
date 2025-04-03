package il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INSERT")
public class InsertRequest extends Request
{
    @Column(name="menu_item_description", nullable=false)
    public String menuItemDescription;

    @Column(name="menu_item_price", nullable=false)
    public Long menuItemPrice;
    @Id
    public Long id;

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

