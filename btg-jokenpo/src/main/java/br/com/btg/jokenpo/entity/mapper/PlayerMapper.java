package br.com.btg.jokenpo.entity.mapper;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.entity.Player;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMapper.class);

    private static final ModelMapper MAPPER = new ModelMapper();

    public static Player requestToPlayerEntity(PlayerRequest playerRequest){
        LOGGER.debug("Converting PlayerRequest object to entity (Player) object");
        MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return MAPPER.map(playerRequest, Player.class);
    }

    public static PlayerResponse entityToResponse(Player player){
        LOGGER.debug("Converting entity (Player) object to PlayerResponse object");
        MAPPER.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return MAPPER.map(player, PlayerResponse.class);
    }
}
