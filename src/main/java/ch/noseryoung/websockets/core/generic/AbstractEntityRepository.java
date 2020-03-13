package ch.noseryoung.websockets.core.generic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AbstractEntityRepository<T extends AbstractEntity> extends JpaRepository<T, String> {

    List<T> findAllByDeletedFalse();

    Optional<T> findByIdAndDeletedFalse(String id);

    boolean existsByIdAndDeletedFalse(String id);

}
