package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Location information")
public class LocationInformation implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name= "City")
    public String city;
    @Column(name = "Street")
    public String street;
    @Column(name = "House number")
    public String houseNumber;

    public LocationInformation() {}
    public LocationInformation(String city, String street, String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }
    public boolean isValid() {
        return city != null && !city.trim().isEmpty()
                && street != null && !street.trim().isEmpty()
                && houseNumber != null && !houseNumber.trim().isEmpty();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getStreet() {
        return street;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public String getHouseNumber() {
        return houseNumber;
    }


}
