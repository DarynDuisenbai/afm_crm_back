package test.afm_crm.log;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final RequestLogRepository requestLogRepository;

    private static final Set<String> EXCLUDED_PREFIXES = Set.of(
            "/swagger-ui", "/v3/api-docs", "/favicon.ico"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            if (shouldLog(request.getRequestURI())) {
                requestLogRepository.save(RequestLog.builder()
                        .username(extractUsername(request))
                        .method(request.getMethod())
                        .uri(request.getRequestURI())
                        .status(response.getStatus())
                        .durationMs(System.currentTimeMillis() - start)
                        .timestamp(LocalDateTime.now())
                        .errorMessage((String) request.getAttribute("log_error"))
                        .build());
            }
        }
    }

    // JwtAuthFilter sets this attribute after successful authentication.
    // Reading it here works even after SecurityContext is cleared.
    private String extractUsername(HttpServletRequest request) {
        String username = (String) request.getAttribute("log_username");
        return username != null ? username : "anonymous";
    }

    private boolean shouldLog(String uri) {
        return EXCLUDED_PREFIXES.stream().noneMatch(uri::startsWith);
    }
}
