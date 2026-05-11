package test.afm_crm.contact;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "contacts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    private String id;

    private String name;

    private String company;

    private String email;

    private String phone;

    private ContactStatus status;

    private LocalDateTime createdAt;
}
