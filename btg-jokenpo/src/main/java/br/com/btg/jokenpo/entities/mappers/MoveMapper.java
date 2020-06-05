package br.com.btg.jokenpo.entities.mappers;

import br.com.btg.jokenpo.dto.MoveResponse;
import br.com.btg.jokenpo.entities.MoveEntity;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveMapper.class);

    private static ModelMapper MAPPER = new ModelMapper();

    public static MoveEntity requestToEntity(MoveEntity moveEntityRequest){
        LOGGER.debug("Converting: request object to entity object");
        return MAPPER.map(moveEntityRequest, MoveEntity.class);
    }

    public static MoveResponse entityToResponse(MoveEntity entity) {
        LOGGER.debug("Converting: entity object to response object");
        MoveResponse response = MAPPER.map(entity, MoveResponse.class);
        response.setMovement(entity.getEnumMovement());
        return response;
    }
}
