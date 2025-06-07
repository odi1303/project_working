package il.cshaifasweng.OCSFMediatorExample.entities.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "branches")
public class BranchEnt implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "branch_name", nullable = false)
    private String branchName;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RestaurantTable> tables;

    public BranchEnt() {}

    public BranchEnt(int id, String branchName) {
        this.id = id;
        this.branchName = branchName;
    }

    public BranchEnt(int id, String branchName, String location, String[] openingHours, List<RestaurantTable> tables) {
        this.id = id;
        this.branchName = branchName;
        this.location = location;
        this.tables = tables;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public void setTables(List<RestaurantTable> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "BranchEnt{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                ", location='" + location + '\'' +
                ", tables=" + tables +
                '}';
    }
}