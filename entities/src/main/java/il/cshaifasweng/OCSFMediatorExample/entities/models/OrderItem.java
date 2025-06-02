package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;


import java.io.Serializable;

@Entity
@Table(name = "order_items")
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MenuItem menuItem;

    private int quantity;

    /*@ManyToOne
    @JoinColumn(name = "order_id")
    private OrderClient order;*/
    public OrderItem(){

    }
    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /*public OrderClient getOrder() {
        return order;
    }

    public void setOrder(OrderClient order) {
        this.order = order;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  menuItem.toString()+",quantity="+quantity;
    }
}