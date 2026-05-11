package test.afm_crm.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.afm_crm.log.dto.UserLogStats;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {

    private final RequestLogRepository requestLogRepository;

    public UserLogStats getStatsByUsername(String username) {
        List<RequestLog> logs = requestLogRepository.findByUsername(username);

        Map<String, Long> byMethod = logs.stream()
                .collect(Collectors.groupingBy(RequestLog::getMethod, Collectors.counting()));

        Map<String, Long> byEndpoint = logs.stream()
                .collect(Collectors.groupingBy(RequestLog::getUri, Collectors.counting()));

        List<RequestLog> recentRequests = logs.stream()
                .sorted(Comparator.comparing(RequestLog::getTimestamp).reversed())
                .limit(10)
                .toList();

        return UserLogStats.builder()
                .username(username)
                .totalRequests(logs.size())
                .byMethod(byMethod)
                .byEndpoint(byEndpoint)
                .recentRequests(recentRequests)
                .build();
    }
}
