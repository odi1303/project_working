package il.cshaifasweng.OCSFMediatorExample.entities.dal.models.requests;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@DiscriminatorValue("DELETE")
public class DeleteRequest extends Request implements Serializable
{
    @Column(name="menu_item_id", nullable=false)
    public Long menuItemId;
    @Id
    public Long id;

    public Long getMenuItem() {
        return menuItemId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

