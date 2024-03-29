package work.szczepanskimichal.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import work.szczepanskimichal.security.filters.PublicAccessFilter;
import work.szczepanskimichal.security.filters.SecurityFilter;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RoutingConfig {

    private final ServiceAddressConfiguration serviceAddressConfiguration;
    private final PublicAccessFilter publicAccessFilter;
    public final SecurityFilter securityFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/v1/public/user/**")
                        .filters(f -> f.filter(publicAccessFilter))
                        .uri(serviceAddressConfiguration.user)) // Public route
                .route(r -> r.path("/v1/user/**")
                        .filters(f -> f.filter(securityFilter))
                        .uri(serviceAddressConfiguration.user))
                .build();
    }
}