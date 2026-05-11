package test.afm_crm.log.dto;

import lombok.Builder;
import lombok.Data;
import test.afm_crm.log.RequestLog;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserLogStats {

    private String username;
    private long totalRequests;
    private Map<String, Long> byMethod;
    private Map<String, Long> byEndpoint;
    private List<RequestLog> recentRequests;
}
