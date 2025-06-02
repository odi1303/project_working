package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import java.io.Serializable;

public class GetBranchClosingTimes implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String branchName;

    public GetBranchClosingTimes(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchName() {
        return branchName;
    }

    @Override
    public String toString() {
        return "GetBranchClosingTimes{" +
                "branchName='" + branchName + '\'' +
                '}';
    }
}
