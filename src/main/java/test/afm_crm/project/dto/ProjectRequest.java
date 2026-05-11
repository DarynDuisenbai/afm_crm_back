package test.afm_crm.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRequest {

    @NotBlank
    private String name;

    private String color;

    private List<String> memberIds;
}
