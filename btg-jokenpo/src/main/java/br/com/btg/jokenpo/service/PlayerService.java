package br.com.btg.jokenpo.service;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.entity.Player;
import br.com.btg.jokenpo.entity.mapper.PlayerMapper;
import br.com.btg.jokenpo.exception.JokenpoException;
import br.com.btg.jokenpo.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void save(PlayerRequest playerRequest) throws JokenpoException{
        LOGGER.debug("Player Data - Request: " + playerRequest.toString());
        Player player = PlayerMapper.requestToPlayerEntity(playerRequest);
        LOGGER.debug("Inserting a new player");
        playerRepository.save(player);
        PlayerMapper.entityToResponse(player);
    }
}
