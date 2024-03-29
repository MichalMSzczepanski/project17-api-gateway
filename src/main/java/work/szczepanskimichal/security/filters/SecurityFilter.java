package work.szczepanskimichal.security.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import work.szczepanskimichal.exception.JwtException;

import java.util.Optional;

@Component
@Slf4j
public class SecurityFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("security filter: check jwt");
        var jwt = extractJwtFromRequest(exchange).orElseThrow(RuntimeException::new);
        //check if jwt is valid (signature, expiration)
        if (!validateJwt(jwt)) {
            throw new JwtException();
        }
        //check if jwt is blacklisted in redis
        //add header with userdetails to request for further validation if needed2
        return chain.filter(exchange);
    }

    private Optional<String> extractJwtFromRequest(ServerWebExchange exchange) {
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return Optional.of(authorizationHeader.substring(7));
        }
        return Optional.empty();
    }

    private boolean validateJwt(String jwt) {
        return ("abc123".equals(jwt));
    }
}
