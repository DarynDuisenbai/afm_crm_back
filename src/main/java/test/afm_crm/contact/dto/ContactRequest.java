package test.afm_crm.contact.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequest {

    @NotBlank
    private String name;

    private String company;

    private String email;

    private String phone;

    private String status;
}
