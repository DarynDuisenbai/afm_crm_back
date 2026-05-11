package test.afm_crm.log;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "request_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog {

    @Id
    private String id;

    @Indexed
    private String username;

    private String method;

    private String uri;

    private int status;

    private long durationMs;

    private LocalDateTime timestamp;

    private String errorMessage;
}
