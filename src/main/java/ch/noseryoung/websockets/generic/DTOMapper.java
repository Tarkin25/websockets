package ch.noseryoung.websockets.generic;

import java.util.Collection;

public interface DTOMapper<DM extends AbstractEntity, DTO extends AbstractDTO> {

    DM fromDTO(DTO dto);

    Collection<DM> fromDTOs(Collection<DTO> dtos);

    DTO toDTO(DM dm);

    Collection<DTO> toDTOs(Collection<DM> dms);

}
