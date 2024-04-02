package work.szczepanskimichal.service;

import feign.FeignException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.exception.AuthenticationException;
import work.szczepanskimichal.exception.InvalidLoginAttemptException;
import work.szczepanskimichal.feign.UserServiceFeignClient;
import work.szczepanskimichal.model.UserLoginDto;
import work.szczepanskimichal.util.CredentialsUtil;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserServiceFeignClient userServiceFeignClient;
    private final CredentialsUtil credentialsUtil;

    public String authenticate(UserLoginDto userLoginDto) {
        try {
            var response =
                    userServiceFeignClient.authenticateUser(userLoginDto).orElseThrow(
                            () -> new AuthenticationException("error during user login processing"));
            var responseStatus = response.getResponseStatus();
            if (responseStatus.equals(HttpStatus.OK)) {
                return Jwts.builder()
                        .setSubject(String.valueOf(response.getUserId()))
                        .claim("email", response.getEmail())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                        .signWith(SignatureAlgorithm.HS256, credentialsUtil.getKey())
                        .compact();
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
