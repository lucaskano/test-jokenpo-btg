package br.com.btg.jokenpo.repository;

import br.com.btg.jokenpo.entity.Move;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.singleton.MoveSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@NoRepositoryBean
public class MoveRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveRepository.class);

    public Move saveMovement(Move move){
        if(MoveSingleton.getInstance() != null){
            MoveSingleton.getInstance().add(move);
            return move;
        }
        LOGGER.error("Error when trying to save the move");
        throw new CustomException("Error when trying to save the move", "Saving Error");
    }

    public List<Move> findAll(){
        if(MoveSingleton.getInstance() == null) {
            LOGGER.error("Error when trying to find all movements");
            throw new CustomException("Error when trying to find all movements", "Find Error");
        }
        return MoveSingleton.getInstance();
    }
}
