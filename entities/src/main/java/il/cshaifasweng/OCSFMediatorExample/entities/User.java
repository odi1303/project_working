package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "password")
    private String password;

    public User(String password, UserType type) {
        this.password = password;
        this.type = type;
        System.out.println(id);
    }

    public boolean isAdmin() {
        return type == UserType.Admin;
    }

    public boolean correctPassword(String password) {
        return this.password.equals(password);
    }

    public UserType getType() {
        return type;
    }

}


class BranchManager extends User {
    private int branchID;

    public BranchManager(String password, UserType type, int branchID) {
        super(password, type);
        this.branchID = branchID;
    }
}