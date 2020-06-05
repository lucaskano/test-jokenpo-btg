package br.com.btg.jokenpo.entities.mappers;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.entities.PlayerEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMapper.class);

    private static final ModelMapper MAPPER = new ModelMapper();

    public static PlayerEntity requestToPlayerEntity(PlayerRequest playerRequest){
        LOGGER.debug("Converting PlayerRequest object to entity (Player) object");
        MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return MAPPER.map(playerRequest, PlayerEntity.class);
    }

    public static PlayerResponse entityToResponse(PlayerEntity playerEntity){
        LOGGER.debug("Converting entity (Player) object to PlayerResponse object");
        MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return MAPPER.map(playerEntity, PlayerResponse.class);
    }
}
