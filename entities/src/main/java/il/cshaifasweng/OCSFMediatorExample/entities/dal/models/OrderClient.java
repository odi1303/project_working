package il.cshaifasweng.OCSFMediatorExample.entities.dal.models;

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
    @ManyToOne
    @JoinColumn(name = "location_information_id")
    private LocationInformation locationInformation;
    @ManyToOne
    @JoinColumn(name = "personal_information_id")
    private PersonalInformation personalInformation;
    @ManyToOne
    @JoinColumn(name = "credit_information_id")
    private CreditInformation creditInformation;
    public OrderClient() {}

    public OrderClient(List<OrderItem> orderItems, boolean isDelivery, LocationInformation locationInformation,
                       PersonalInformation personalInformation, CreditInformation creditInformation) {
        this.orderItems = orderItems;
        this.isDelivery = isDelivery;
        this.locationInformation = locationInformation;
        this.personalInformation = personalInformation;
        this.creditInformation = creditInformation;
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
}
