package org.example.finalproject.dal.models.requests;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("INSERT")
public class InsertRequest extends Request
{
    @Column(name="menu_item_description", nullable=false)
    private String menuItemDescription;

    @Column(name="menu_item_price", nullable=false)
    private Long menuItemPrice;

    public String getMenuItemDescription() {
        return menuItemDescription;
    }

    public Long getMenuItemPrice() {
        return menuItemPrice;
    }
}

