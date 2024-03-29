package work.szczepanskimichal.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import work.szczepanskimichal.model.LoginResponse;
import work.szczepanskimichal.model.UserLoginDto;

import java.util.Optional;

@FeignClient(name = "user-service", url = "${services.address.user}")
public interface UserServiceFeignClient {

    @PostMapping("/v1/internal/user/authenticate")
    Optional<LoginResponse> authenticateUser(@RequestBody UserLoginDto request);

}
