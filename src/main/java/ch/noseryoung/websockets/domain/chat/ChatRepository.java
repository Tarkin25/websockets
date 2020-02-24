package ch.noseryoung.websockets.domain.chat;

import ch.noseryoung.websockets.generic.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends AbstractEntityRepository<Chat> {
}
