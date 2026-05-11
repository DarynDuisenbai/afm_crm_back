package test.afm_crm.task.dto;

import lombok.Builder;
import lombok.Data;
import test.afm_crm.task.TaskPriority;
import test.afm_crm.task.TaskStatus;
import test.afm_crm.user.dto.UserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {

    private String id;
    private String title;
    private TaskStatus status;
    private TaskPriority priority;
    private String projectId;
    private UserResponse assignee;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
