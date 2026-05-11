package test.afm_crm.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStats {

    private long totalTasks;
    private long inProgressTasks;
    private long doneTasks;
    private long totalContacts;
    private long leads;
}
