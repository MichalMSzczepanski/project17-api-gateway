package work.szczepanskimichal.filters;

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
import java.util.regex.Pattern;

@Component
@Slf4j
public class InternalAccessFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isInternalRequest(exchange.getRequest())) {
            log.info("internal request: requestId: " + exchange.getRequest().getId() +
                    ". path: " + exchange.getRequest().getURI().getPath());
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isInternalRequest(ServerHttpRequest request) {
        var ipAddress = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        log.info("internal request from ip: " + ipAddress);
        var pattern = Pattern.compile("(localhost|\\b(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}" +
                "(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)(?::\\d{0,4})?\\b)");
        var matcher = pattern.matcher(ipAddress);
        return matcher.matches() || ipAddress.equals("0:0:0:0:0:0:0:1%0");
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
