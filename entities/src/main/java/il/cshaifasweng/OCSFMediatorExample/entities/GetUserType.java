package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class GetUserType implements Serializable {
    public String name;
    public String password;
    public GetUserType(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
