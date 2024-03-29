package work.szczepanskimichal.security.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class PublicAccessFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("public access endpoint accessed. method: " + exchange.getRequest().getMethod() +
                ", URI: " + exchange.getRequest().getURI());
        return chain.filter(exchange);
    }

}
