package br.com.btg.jokenpo.repositories;

import br.com.btg.jokenpo.entities.MoveEntity;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import br.com.btg.jokenpo.singletons.MoveSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ATTENTION: The JPARepository extending interface was not used due to not using the database.
 * However, it was decided to follow the Singleton pattern in order to store the data.
 *
 */

@Repository
@NoRepositoryBean
public class MoveRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveRepository.class);

    public MoveEntity saveMovement(MoveEntity moveEntity){
        if(MoveSingleton.getInstance() != null){
            MoveSingleton.getInstance().add(moveEntity);
            return moveEntity;
        }
        LOGGER.error("Error when trying to save the move");
        throw new CustomException("Error when trying to save the move", "Saving Error");
    }

    public List<MoveEntity> findAll(){
        if(MoveSingleton.getInstance() == null) {
            LOGGER.error("Error when trying to find all movements");
            throw new CustomException("Error when trying to find all movements", "Find Error");
        }
        return MoveSingleton.getInstance();
    }

    public MoveEntity findByPlayerName(String playerName){
        List<MoveEntity> movesList = findAll().stream()
                .filter(element -> element.getPlayerEntity().getPlayerName().compareToIgnoreCase(playerName) == 0)
                .collect(Collectors.toList());
        Optional<MoveEntity> optional = movesList.stream().findFirst();
        if(optional.isPresent()){
            return optional.get();
        }
        LOGGER.error("The player {} does not have a move", playerName);
        throw new ObjectNotFoundException("The player " + playerName + " does not have a move");
    }

    public boolean delete(MoveEntity moveEntity){
        if(MoveSingleton.getInstance() == null){
            LOGGER.error("Error deleting the movement ");
            throw new CustomException("Error deleting the movement", "Error deleting");
        }
        return MoveSingleton.getInstance().remove(moveEntity);
    }
}
