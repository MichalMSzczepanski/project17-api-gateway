package work.szczepanskimichal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import work.szczepanskimichal.model.BlacklistedJwt;

public interface BlacklistedJwtsRepository extends MongoRepository<BlacklistedJwt, String> {

    boolean existsByJwt(String jwt);

}