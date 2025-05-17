package il.cshaifasweng.OCSFMediatorExample.entities.dal.models.requests;

import java.io.Serializable;

public class GetBranchOpeningTimes implements Serializable {

    private final String branchName;

    public GetBranchOpeningTimes(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchName() {
        return branchName;
    }

    @Override
    public String toString() {
        return "GetBranchOpeningTimes{" +
                "branchName='" + branchName + '\'' +
                '}';
    }
}
