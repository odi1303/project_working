package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class VerifyPassword implements Serializable {
    public String name;
    public String password;
    public VerifyPassword(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
