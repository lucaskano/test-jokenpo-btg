package br.com.btg.jokenpo.repository;


import br.com.btg.jokenpo.entity.Player;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import br.com.btg.jokenpo.singleton.PlayerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@NoRepositoryBean
public class PlayerRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerRepository.class);

    public List<Player> findAll(){
        if(PlayerSingleton.getInstance() == null){
            LOGGER.error("Error trying to find players");
            throw new CustomException("Error trying to find players", "Find Error");
        }
        return PlayerSingleton.getInstance();
    }

    public Player save(Player player){
        if(PlayerSingleton.getInstance() != null) {
            PlayerSingleton.getInstance().add(player);
            return player;
        }
        LOGGER.error("Error when trying to save a new player");
        throw new CustomException("Error when trying to save a new player", "Save Error");
    }

    public boolean delete(Player player){
        if(PlayerSingleton.getInstance() == null){
            LOGGER.error("Error when trying to delete the player {}", player.getPlayerName());
            throw new CustomException("Error when trying to delete the player", "Delete Error");
        }
        return PlayerSingleton.getInstance().remove(player);
    }

    public Player findByName (String name){
        List<Player> playersList = findAll().stream()
                .filter(element -> (element.getPlayerName().compareToIgnoreCase(name) == 0))
                .collect(Collectors.toList());
        Optional<Player> optional = playersList.stream().findFirst();
        if(optional.isPresent()){
            return optional.get();
        }
        LOGGER.error("Player not found for name: {}", name);
        throw new ObjectNotFoundException(String.format("Player %s not found", name));
    }

}
