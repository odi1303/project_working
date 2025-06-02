package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;
//import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
public class OrderClient implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    private boolean isDelivery;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_information_id")
    private LocationInformation locationInformation;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_information_id")
    private PersonalInformation personalInformation;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_information_id")
    private CreditInformation creditInformation;
    @Column(name = "order time")
    private Date orderTime;// the time the order was placed
    @Column
    private Date deliveryTime;// the time the delivery arrived

    public OrderClient() {}

    public OrderClient(List<OrderItem> orderItems, boolean isDelivery, LocationInformation locationInformation,
                       PersonalInformation personalInformation, CreditInformation creditInformation) {
        this.orderItems = orderItems;
        this.isDelivery = isDelivery;
        this.locationInformation = locationInformation;
        this.personalInformation = personalInformation;
        this.creditInformation = creditInformation;
        this.orderTime = new Date();
        this.deliveryTime=null;
    }

    public CreditInformation getCreditInformation() {
        return creditInformation;
    }

    public void setCreditInformation(CreditInformation creditInformation) {
        this.creditInformation = creditInformation;
    }

    public LocationInformation getLocationInformation() {
        return locationInformation;
    }

    public void setLocationInformation(LocationInformation locationInformation) {
        this.locationInformation = locationInformation;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryTime(){
        this.deliveryTime=new Date();
    }
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        String items="Your order's items are:\n";
        for (OrderItem orderItem : orderItems) {
            items+=orderItem.toString()+"\n";
        }
        return "OrderClient{" +
                "id=" + id +
                "\n"+items+
                ", isDelivery=" + isDelivery +
                ",\n locationInformation=" + locationInformation +
                ",\n personalInformation=" + personalInformation +
                '}';
    }
}
