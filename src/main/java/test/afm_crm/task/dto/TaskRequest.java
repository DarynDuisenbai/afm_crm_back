package test.afm_crm.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotBlank
    private String title;

    private String status;

    private String priority;

    private String projectId;

    private String assigneeId;

    private LocalDate dueDate;
}
