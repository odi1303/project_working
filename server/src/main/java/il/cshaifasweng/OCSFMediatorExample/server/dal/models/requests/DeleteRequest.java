package il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("DELETE")
public class DeleteRequest extends Request
{
    @Column(name="menu_item_id", nullable=false)
    private Long menuItemId;
    @Id
    private Long id;

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

