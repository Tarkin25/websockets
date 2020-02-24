package ch.noseryoung.websockets.domain.chat.dto;

import ch.noseryoung.websockets.domain.chat.Chat;
import ch.noseryoung.websockets.generic.DTOMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper extends DTOMapper<Chat, ChatDTO> {
}
