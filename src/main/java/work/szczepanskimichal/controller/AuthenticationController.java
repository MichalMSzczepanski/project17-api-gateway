package work.szczepanskimichal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import work.szczepanskimichal.model.UserLoginDto;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto dto) {
        return ResponseEntity.ok("lorem");
    }

    //logout
    //disable jwt
}
