package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class GetBranchOpeningTimes implements Serializable {

    private String branchName;

    public GetBranchOpeningTimes(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
