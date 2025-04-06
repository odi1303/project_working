package il.cshaifasweng.OCSFMediatorExample.server.dal.models;

import il.cshaifasweng.OCSFMediatorExample.entities.User;
import il.cshaifasweng.OCSFMediatorExample.entities.UserType;

public class BranchManager extends il.cshaifasweng.OCSFMediatorExample.entities.User {
    private int branchID;

    public BranchManager(int id, String password, int branchID, String userName) {
        super(password, UserType.BranchManager);
        this.branchID = branchID;
    }

    public int getBranchID() {
        return branchID;
    }
}