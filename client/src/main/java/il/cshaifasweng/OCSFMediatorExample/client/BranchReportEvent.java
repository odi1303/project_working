package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.models.BranchReportEnt;

public class BranchReportEvent {
    private BranchReportEnt branchReport;
    public BranchReportEvent(BranchReportEnt branchReport) {
        this.branchReport = branchReport;
    }
    public BranchReportEnt getBranchReport() {
        return branchReport;
    }
}