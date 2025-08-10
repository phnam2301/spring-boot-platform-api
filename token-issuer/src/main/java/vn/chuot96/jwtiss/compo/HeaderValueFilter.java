package vn.chuot96.jwtiss.compo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class HeaderValueFilter extends OncePerRequestFilter {
    private final HeaderValueAllowed headerValueAllowed;

    private final ExternalFileReader externalFileReader;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/issue/")) {
            String token = request.getHeader(externalFileReader.string("InternalHeaderName"));
            if (token == null || !headerValueAllowed.isAllowed(token)) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or missing internal token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
