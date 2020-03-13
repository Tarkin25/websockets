package ch.noseryoung.websockets.domain.user.dto;

import ch.noseryoung.websockets.domain.user.User;
import ch.noseryoung.websockets.core.generic.DTOMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends DTOMapper<User, UserDTO> {

    User fromDTO(UserDTO.WithPassword dto);

}
