package ch.noseryoung.websockets.generic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

public abstract class AbstractController<DM extends AbstractEntity, DTO extends AbstractDTO> {

    protected AbstractEntityService<DM> service;
    protected DTOMapper<DM, DTO> mapper;

    public AbstractController(AbstractEntityService<DM> service, DTOMapper<DM, DTO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Collection<DTO>> findAll() {
        Collection<DM> dms = service.findAll();

        return new ResponseEntity<>(mapper.toDTOs(dms), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> findById(@PathVariable String id) {
        DM dm = service.findById(id);

        return new ResponseEntity<>(mapper.toDTO(dm), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DTO> create(@RequestBody @Valid DTO dto) {
        DM dm = service.create(mapper.fromDTO(dto));

        return new ResponseEntity<>(mapper.toDTO(dm), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> updateById(@PathVariable String id, @RequestBody @Valid DTO dto) {
        DM dm = service.updateById(id, mapper.fromDTO(dto));

        return new ResponseEntity<>(mapper.toDTO(dm), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}