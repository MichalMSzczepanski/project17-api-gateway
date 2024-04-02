package work.szczepanskimichal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import work.szczepanskimichal.model.BlacklistedJwt;
import work.szczepanskimichal.repository.BlacklistedJwtsRepository;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final BlacklistedJwtsRepository blacklistedJwtsRepository;

    public void blacklistJwt(String jwt) {
        var blacklistedJwt = BlacklistedJwt.builder()
                .jwt(jwt.substring(7))
                .build();
        blacklistedJwtsRepository.save(blacklistedJwt);
    }

}
