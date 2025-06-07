package il.cshaifasweng.OCSFMediatorExample.entities.clientRequests;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class GetBranchReportRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private int branchId;
    private int year;
    private int month;

    public GetBranchReportRequest(int branchId, int year, int month) {
        this.branchId = branchId;
        this.year = year;
        this.month = month;
    }

}