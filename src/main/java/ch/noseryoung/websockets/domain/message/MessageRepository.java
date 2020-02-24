package ch.noseryoung.websockets.domain.message;

import ch.noseryoung.websockets.generic.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends AbstractEntityRepository<Message> {
}
