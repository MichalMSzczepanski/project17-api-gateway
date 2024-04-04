package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

@Service
@RequiredArgsConstructor
public class FilterService {

    public ServerWebExchange addHeader(ServerWebExchange exchange, String headerName, String headerValue) {
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(headerName, headerValue)
                .build();
        return exchange.mutate().request(mutatedRequest).build();
    }

}
