package il.cshaifasweng.OCSFMediatorExample.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    public MenuItem() {
    }

    public int getId() {
        return id;
    }

    public void disable() {
        isEnabled = false;
    }
}
