package work.szczepanskimichal.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import work.szczepanskimichal.exception.JwtException;
import work.szczepanskimichal.exception.MissingJwtException;
import work.szczepanskimichal.model.Headers;
import work.szczepanskimichal.repository.BlacklistedJwtsRepository;
import work.szczepanskimichal.service.FilterService;
import work.szczepanskimichal.util.CredentialsUtil;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter implements GatewayFilter {

    private final CredentialsUtil credentialsUtil;
    private final BlacklistedJwtsRepository blacklistedJwtsRepository;
    private final FilterService filterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var jwt = extractJwtFromRequest(exchange).orElseThrow(MissingJwtException::new);
        if (!isJwtValid(jwt)) {
            throw new JwtException("invalid jwt");
        }
        if (blacklistedJwtsRepository.existsByJwt(jwt)) {
            throw new JwtException("jwt expired");
        }
        var userId = extractClaimByName(jwt, "userId");
        exchange = filterService.addHeader(exchange, Headers.X_USER_ID, userId);
        return chain.filter(exchange);
    }

    private Optional<String> extractJwtFromRequest(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return Optional.of(authorizationHeader.substring(7));
        }
        return Optional.empty();
    }

    private boolean isJwtValid(String jwt) {
        try {
            Jws<Claims> claims = extractClaims(jwt);
            if (claims.getBody().getExpiration().before(new Date())) {
                throw new JwtException("expired jwt");
            }
            return true;
        } catch (Exception e) {
            log.error("error validating jwt: {}", e.getMessage());
            return false;
        }
    }

    private Jws<Claims> extractClaims(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(credentialsUtil.getKey())
                .build()
                .parseClaimsJws(jwt);
    }

    private String extractClaimByName(String jwt, String claim) {
        try {
            Jws<Claims> claims = extractClaims(jwt);
            return claims.getBody().get("userId", String.class);
        } catch (Exception e) {
            log.error("error validating jwt: {}", e.getMessage());
            throw new JwtException(String.format("error extractig claim: %s", claim));
        }
    }
}
