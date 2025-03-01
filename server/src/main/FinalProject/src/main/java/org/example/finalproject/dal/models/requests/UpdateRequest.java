package org.example.finalproject.dal.models.requests;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("UPDATE")
public class UpdateRequest extends Request
{
    @Column(name="menu_item_id", nullable = false)
    private int menuItemId;

    @Column(name="menu_item_description")
    private String menuItemDescription;

    @Column(name="menu_item_price")
    private Long menuItemPrice;

    public UpdateRequest() { }

    public int getMenuItem() {
        return menuItemId;
    }

    public String getMenuItemDescription() {
        return menuItemDescription;
    }

    public Long getMenuItemPrice() {
        return menuItemPrice;
    }
}

