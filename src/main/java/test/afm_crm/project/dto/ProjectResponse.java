package test.afm_crm.project.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectResponse {

    private String id;
    private String name;
    private String color;
    private long totalTasks;
    private long doneTasks;
    private List<MemberInfo> members;
}
