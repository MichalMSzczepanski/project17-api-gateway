package work.szczepanskimichal.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.AuthenticationException;
import work.szczepanskimichal.exception.InvalidLoginAttemptException;
import work.szczepanskimichal.feign.UserServiceFeignClient;
import work.szczepanskimichal.model.UserLoginDto;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserServiceFeignClient userServiceFeignClient;

    public String authenticate(UserLoginDto userLoginDto) {
        try {
            var response =
                    userServiceFeignClient.authenticateUser(userLoginDto).orElseThrow(
                            () -> new AuthenticationException("error during user login processing"));
            var responseStatus = response.getResponseStatus();
            if (responseStatus.equals(HttpStatus.OK)) {
                //todo generate jwt based on the returned LoginResponse data
                return "123abc";
            } else if (responseStatus.equals(HttpStatus.UNAUTHORIZED)) {
                throw new InvalidLoginAttemptException();
            } else if (responseStatus.is4xxClientError()) {
                throw new AuthenticationException(response.getMessage());
            }
        } catch (FeignException e) {
            throw new AuthenticationException("communication error during authentication");
        }
        throw new AuthenticationException("system error during authentication");
    }

}
