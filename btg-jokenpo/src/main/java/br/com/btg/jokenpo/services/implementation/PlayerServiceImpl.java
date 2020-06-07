package br.com.btg.jokenpo.services.implementation;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.entities.PlayerEntity;
import br.com.btg.jokenpo.entities.mappers.PlayerMapper;
import br.com.btg.jokenpo.repositories.MoveRepository;
import br.com.btg.jokenpo.services.PlayerService;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.repositories.PlayerRepository;
import br.com.btg.jokenpo.services.exceptions.ContentAlreadyExistsException;
import br.com.btg.jokenpo.singletons.PlayerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MoveServiceImpl moveService;

    public PlayerServiceImpl() {

    }

    @Override
    public List<PlayerResponse> findAll(){
        LOGGER.debug("Searching all players");
        List<PlayerEntity> playersList = playerRepository.findAll();
        List<PlayerResponse> responseList = new ArrayList<>();
        playersList.forEach(element -> responseList.add(PlayerMapper.entityToResponse(element)));
        LOGGER.debug("Listed players");
        return responseList;
    }

    @Override
    public PlayerEntity findByName(String name){
        LOGGER.debug("Searching the player {}", name);
        return playerRepository.findByName(name);
    }

    @Override
    public PlayerResponse save(PlayerRequest player) {
        if(this.checkIfAlreadyExistsByName(player.getPlayerName())){
            LOGGER.error("Player already exists");
            throw new CustomException("Player already exists", "Player already exists");
        }
        LOGGER.debug("Insert new player - Request: " + player.toString());
        PlayerEntity entity = PlayerMapper.requestToPlayerEntity(player);
        LOGGER.debug("Inserting player");
        entity = this.playerRepository.save(entity);
        LOGGER.debug("Creating response object");
        return PlayerMapper.entityToResponse(entity);
    }

    @Override
    public List<PlayerResponse> deleteByName(String name){
        if (StringUtils.isEmpty(name)) {
            LOGGER.error("Parameter name is invalid");
            throw new CustomException("Parameter name is invalid", "Invalid Parameter");
        }
        try{
            moveService.deleteByPlayerName(name);
        }catch(CustomException e){
            LOGGER.debug("Player without movement");
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
        } catch (CustomException e) {
            return false;
        }
        return false;
    }

    public void clearAll() {
        PlayerSingleton.clear();
    }
}
