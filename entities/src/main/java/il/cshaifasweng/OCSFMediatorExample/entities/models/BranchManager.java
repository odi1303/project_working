package il.cshaifasweng.OCSFMediatorExample.entities.models;

import java.io.Serializable;

public class BranchManager extends User implements Serializable {
    private int branchID;

    public BranchManager(int id, String password, int branchID, String userName) {
        this.branchID = branchID;
    }

    public int getBranchID() {
        return branchID;
    }
}