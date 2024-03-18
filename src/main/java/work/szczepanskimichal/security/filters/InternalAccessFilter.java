package work.szczepanskimichal.security.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class InternalAccessFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isInternalRequest(exchange.getRequest())) {
            log.info("attempted internal service accessed. requestId: " + exchange.getRequest().getId() +
                    ". path: " + exchange.getRequest().getURI().getPath());
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isInternalRequest(ServerHttpRequest request) {
        var ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        return ipAddress.equals("localhost") || ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1%0");
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
