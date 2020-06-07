package br.com.btg.jokenpo.repositories;


import br.com.btg.jokenpo.entities.PlayerEntity;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import br.com.btg.jokenpo.singletons.PlayerSingleton;
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

    public List<PlayerEntity> findAll(){
        if(PlayerSingleton.getInstance() == null){
            LOGGER.error("Error trying to find players");
            throw new CustomException("Error trying to find players", "Find Error");
        }
        return PlayerSingleton.getInstance();
    }

    public PlayerEntity save(PlayerEntity playerEntity){
        if(PlayerSingleton.getInstance() != null) {
            PlayerSingleton.getInstance().add(playerEntity);
            return playerEntity;
        }
        LOGGER.error("Error when trying to save a new player");
        throw new CustomException("Error when trying to save a new player", "Save Error");
    }

    public boolean delete(PlayerEntity playerEntity){
        if(PlayerSingleton.getInstance() == null){
            LOGGER.error("Error when trying to delete the player {}", playerEntity.getPlayerName());
            throw new CustomException("Error when trying to delete the player", "Delete Error");
        }
        return PlayerSingleton.getInstance().remove(playerEntity);
    }

    public PlayerEntity findByName(String name){
        List<PlayerEntity> list = findAll().stream()
                .filter(elem -> (elem.getPlayerName().compareToIgnoreCase(name) == 0))
                .collect(Collectors.toList());
        Optional<PlayerEntity> opt = list.stream().findFirst();
        if(opt.isPresent()){
            return opt.get();
        }
        LOGGER.info("Player not found : {}", name);
        throw new CustomException("Player not found", "Object not found");
    }

}
