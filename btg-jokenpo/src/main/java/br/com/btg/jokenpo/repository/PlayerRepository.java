package br.com.btg.jokenpo.repository;


import br.com.btg.jokenpo.entity.Player;
import br.com.btg.jokenpo.enumeration.EnumException;
import br.com.btg.jokenpo.exception.JokenpoException;
import br.com.btg.jokenpo.singleton.PlayerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NoRepositoryBean
public class PlayerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepository.class);

    public List<Player> findAll() throws JokenpoException{
        if(PlayerSingleton.getInstance() == null){
            LOGGER.error("Error trying to find players");
            throw new JokenpoException(EnumException.PLAYER_FIND_ALL_ERROR);
        }
        return PlayerSingleton.getInstance();
    }

    public Player save(Player player) throws JokenpoException{
        if(PlayerSingleton.getInstance() != null){
            PlayerSingleton.getInstance().add(player);
            return player;
        }
        LOGGER.error("Error when trying to save a new player");
        throw new JokenpoException(EnumException.PLAYER_SAVING_ERROR);
    }
}
