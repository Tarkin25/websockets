package ch.noseryoung.websockets.domain.message.dto;

import ch.noseryoung.websockets.domain.message.Message;
import ch.noseryoung.websockets.core.generic.DTOMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper extends DTOMapper<Message, MessageDTO> {

    @Override
    @Mapping(target = "timestamp", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    MessageDTO toDTO(Message message);

    @Override
    @Mapping(target = "timestamp", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    Message fromDTO(MessageDTO dto);
}
