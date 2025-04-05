package il.cshaifasweng.OCSFMediatorExample.client;

public class LocationInformation {
    private final String city;
    private final String street;
    private final String houseNumber;

    public LocationInformation(String city, String street, String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }
    public String getCity() {
        return city;
    }


    public String getStreet() {
        return street;
    }


    public String getHouseNumber() {
        return houseNumber;
    }


    public boolean isValid() {
        return city != null && !city.trim().isEmpty()
                && street != null && !street.trim().isEmpty()
                && houseNumber != null && !houseNumber.trim().isEmpty();
    }
}
