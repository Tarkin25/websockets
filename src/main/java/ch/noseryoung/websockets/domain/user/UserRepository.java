package ch.noseryoung.websockets.domain.user;

import ch.noseryoung.websockets.generic.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AbstractEntityRepository<User> {

    Optional<User> findByUsernameAndDeletedFalse(String username);

}
