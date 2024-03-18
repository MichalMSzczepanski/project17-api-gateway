package work.szczepanskimichal.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.szczepanskimichal.security.filters.DebugFilter;
import work.szczepanskimichal.security.filters.InternalAccessFilter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RoutingConfig {

    private final ServiceAddressConfiguration serviceAddressConfiguration;
    private final DebugFilter debugFilter;
    private final InternalAccessFilter internalAccessFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/public/user/**")
                        .filters(f -> f.filter(debugFilter))
                        .uri(serviceAddressConfiguration.user)) // Public route
                .route(r -> r.path("/user/**")
                        // needs jwt authentication
                        // .filters(f -> f.filter(jwtFilter))
                        .uri(serviceAddressConfiguration.user)) // Private route
                .route(r -> r.path("/internal/user/**")
                        .filters(f -> f.filter(internalAccessFilter))
                        .uri(serviceAddressConfiguration.user))
                .route(r -> r.path("/secret-key/**")
                        .filters(f -> f.filter(internalAccessFilter))
                        .uri(serviceAddressConfiguration.user)) // Route for /secret-key
                .build();
    }
}