package br.com.btg.jokenpo.service;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.entity.Player;
import br.com.btg.jokenpo.entity.mapper.PlayerMapper;
import br.com.btg.jokenpo.enumeration.EnumException;
import br.com.btg.jokenpo.exception.JokenpoException;
import br.com.btg.jokenpo.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public PlayerService(){

    }

    public List<PlayerResponse> findAll() throws JokenpoException{
        LOGGER.debug("Searching all players");
        List<Player> playersList = playerRepository.findAll();
        List<PlayerResponse> responseList = new ArrayList<>();
        playersList.forEach(element -> {
            responseList.add(PlayerMapper.entityToResponse(element));
        });
        LOGGER.debug("Listed players");
        return responseList;
    }

    public Player findByName(String name) throws JokenpoException{
        LOGGER.debug("Searching the player " + name);
        return playerRepository.findByName(name);
    }

    public void save(PlayerRequest playerRequest) throws JokenpoException{
        LOGGER.debug("Player Data - Request: " + playerRequest.toString());
        Player player = PlayerMapper.requestToPlayerEntity(playerRequest);
        LOGGER.debug("Inserting a new player");
        playerRepository.save(player);
        PlayerMapper.entityToResponse(player);
    }

    public List<PlayerResponse> deleteByName(String name) throws JokenpoException{
        if(StringUtils.isEmpty(name)){
            LOGGER.error("Parameter name is invalid");
            throw new JokenpoException(EnumException.PARAM_ERROR);
        }
            LOGGER.debug("Searching the player " + name);
            Player player = playerRepository.findByName(name);
            LOGGER.debug("Removing player");
            if(playerRepository.delete(player)){
                LOGGER.debug("Player Deleted " + player.getName());
                return this.findAll();
            }
            LOGGER.error("Error deleting player");
            throw new JokenpoException(EnumException.PLAYER_DELETE_ERROR);
    }

    private Boolean verifyIfAlreadyExistsByName(String name) {
        try {
            if (!Objects.isNull(this.playerRepository.findByName(name))) {
                return true;
            }
        } catch (JokenpoException e) {
            return false;
        }
        return false;
    }
}
