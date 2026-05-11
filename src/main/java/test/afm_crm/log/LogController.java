package test.afm_crm.log;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.afm_crm.log.dto.UserLogStats;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
@Tag(name = "Logs")
public class LogController {

    private final LogService logService;

    @GetMapping("/stats/{username}")
    @Operation(summary = "Get request statistics for a user")
    public ResponseEntity<UserLogStats> getStatsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(logService.getStatsByUsername(username));
    }
}
