package br.com.btg.jokenpo.services;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.entities.PlayerEntity;
import br.com.btg.jokenpo.entities.mappers.PlayerMapper;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.repositories.PlayerRepository;
import br.com.btg.jokenpo.services.exceptions.ContentAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerService() {

    }

    public List<PlayerResponse> findAll(){
        LOGGER.debug("Searching all players");
        List<PlayerEntity> playersList = playerRepository.findAll();
        List<PlayerResponse> responseList = new ArrayList<>();
        playersList.forEach(element -> responseList.add(PlayerMapper.entityToResponse(element)));
        LOGGER.debug("Listed players");
        return responseList;
    }

    public PlayerEntity findByName(String name){
        LOGGER.debug("Searching the player {}", name);
        return playerRepository.findByName(name);
    }

    public PlayerResponse save(PlayerRequest playerRequest){
        if (this.checkIfAlreadyExistsByName(playerRequest.getPlayerName())) {
            LOGGER.error("Player with that name already exists");
            throw new ContentAlreadyExistsException("Player with that name already exists");
        }
        LOGGER.debug("Player Data - Request: {}", playerRequest);
        PlayerEntity playerEntity = PlayerMapper.requestToPlayerEntity(playerRequest);
        LOGGER.debug("Inserting a new player");
        playerRepository.save(playerEntity);
        LOGGER.debug("Creating the object response (PlayerResponse)");
        return PlayerMapper.entityToResponse(playerEntity);
    }

    public List<PlayerResponse> deleteByName(String name){
        if (StringUtils.isEmpty(name)) {
            LOGGER.error("Parameter name is invalid");
            throw new CustomException("Parameter name is invalid", "Invalid Parameter");
        }
        LOGGER.debug("Searching the player {}", name);
        PlayerEntity playerEntity = playerRepository.findByName(name);
        LOGGER.debug("Removing player");
        if (playerRepository.delete(playerEntity)) {
            LOGGER.debug("Player Deleted {}", playerEntity.getPlayerName());
            return this.findAll();
        }
        LOGGER.error("Error deleting player");
        throw new CustomException("Error deleting player", "Delete Error");
    }

    private Boolean checkIfAlreadyExistsByName(String name) {
        try {
            if (!Objects.isNull(this.playerRepository.findByName(name))) {
                return true;
            }
        } catch (RuntimeException e) {
            return false;
        }
        return false;
    }
}
