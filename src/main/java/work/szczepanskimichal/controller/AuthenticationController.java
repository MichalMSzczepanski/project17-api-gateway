package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import work.szczepanskimichal.model.UserLoginDto;
import work.szczepanskimichal.service.JwtService;
import work.szczepanskimichal.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/v1/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto dto) {
        return ResponseEntity.ok(userService.authenticate(dto));
    }

    @PostMapping("/v1/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String jwt) {
        jwtService.blacklistJwt(jwt);
        return ResponseEntity.ok("successfully logged out");
    }
}
