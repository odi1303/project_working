package org.example.finalproject.dal.models.requests;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("DELETE")
public class DeleteRequest extends Request
{
    @Column(name="menu_item_id", nullable=false)
    private int menuItemId;
    public int getMenuItem() {
        return menuItemId;
    }
}

