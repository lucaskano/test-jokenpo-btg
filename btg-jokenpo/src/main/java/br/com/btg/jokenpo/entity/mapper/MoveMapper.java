package br.com.btg.jokenpo.entity.mapper;

import br.com.btg.jokenpo.dto.MoveResponse;
import br.com.btg.jokenpo.entity.Move;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveMapper.class);

    private static ModelMapper MAPPER = new ModelMapper();

    public static Move requestToEntity(Move moveRequest){
        LOGGER.debug("Converting: request object to entity object");
        return MAPPER.map(moveRequest, Move.class);
    }

    public static MoveResponse entityToResponse(Move entity) {
        LOGGER.debug("Converting: entity object to response object");
        MoveResponse response = MAPPER.map(entity, MoveResponse.class);
        response.setMovement(entity.getEnumMovement());
        return response;
    }
}
