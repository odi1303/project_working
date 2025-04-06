package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @Column(name = "username")
    private String userName;

    public User(String password, UserType type) {
        this.type = type;
        System.out.println(id);
    }

    public boolean isAdmin() {
        return type == UserType.Admin;
    }

    public boolean correctPassword(int password) {
        return this.id == password;
    }

    public UserType getType() {
        return type;
    }

}