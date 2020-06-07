package br.com.btg.jokenpo.services.implementation;

import br.com.btg.jokenpo.dto.MoveRequest;
import br.com.btg.jokenpo.dto.MoveResponse;
import br.com.btg.jokenpo.entities.MoveEntity;
import br.com.btg.jokenpo.entities.PlayerEntity;
import br.com.btg.jokenpo.entities.mappers.MoveMapper;
import br.com.btg.jokenpo.enumeration.EnumMovement;
import br.com.btg.jokenpo.repositories.MoveRepository;
import br.com.btg.jokenpo.repositories.PlayerRepository;
import br.com.btg.jokenpo.services.MoveService;
import br.com.btg.jokenpo.services.exceptions.ContentAlreadyExistsException;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import br.com.btg.jokenpo.singletons.MoveSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MoveServiceImpl implements MoveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveServiceImpl.class);

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public MoveResponse insertMove(MoveRequest moveRequest){
        if(Objects.isNull(moveRequest)
                || StringUtils.isEmpty(moveRequest.getPlayerName())
                || StringUtils.isEmpty(moveRequest.getMovement())){
            LOGGER.error("Invalid movement");
            throw new CustomException("Invalid Movement", "Parameter is null");
        }
        LOGGER.debug("Move: {}", moveRequest);

        //Get the player associated with the move
        PlayerEntity playerEntity = playerRepository.findByName(moveRequest.getPlayerName());

        //Checks if the player already has a move
        checkIfAlreadyMoved(playerEntity);

        //Get the movement
        EnumMovement movement = EnumMovement.getEnumMovementByName(moveRequest.getMovement());

        if(Objects.isNull(movement)){
            LOGGER.error("Movement not found");
            throw new ObjectNotFoundException("Movement not found");
        }

        //Save move
        MoveEntity moveEntity = moveRepository.saveMovement(new MoveEntity(playerEntity, movement));

        //Convert Move (entity) to response
        return MoveMapper.entityToResponse(moveEntity);
    }

    @Override
    public List<MoveResponse> getAll(){
        LOGGER.debug("Finding all movements");
        List<MoveEntity> movesList = this.moveRepository.findAll();
        List<MoveResponse> movesResponse = new ArrayList<>();
        movesList.forEach(element -> movesResponse.add(MoveMapper.entityToResponse(element)));
        LOGGER.debug("Movements searched");
        return movesResponse;
    }

    @Override
    public List<MoveResponse> deleteByPlayerName(String playerName){
        if(StringUtils.isEmpty(playerName)){
            LOGGER.error("Player name is invalid");
            throw new CustomException("The playerName parameter is invalid", "Invalid Parameter");
        }
        LOGGER.debug("Finding {} movement", playerName);
        MoveEntity moveEntity = this.moveRepository.findByPlayerName(playerName);
        LOGGER.debug("Deleting movement");
        if(this.moveRepository.delete(moveEntity)){
            return this.getAll();
        };
        LOGGER.error("Error trying to delete the movement");
        throw new CustomException("Error trying to delete the movement", "Error deleting");
    }

    private void checkIfAlreadyMoved(PlayerEntity playerEntity){
        long count = moveRepository.findAll().stream()
                .filter(element ->
                        (element.getPlayerEntity().getPlayerName()).compareToIgnoreCase(playerEntity.getPlayerName()) == 0)
                .count();
        if(count > 0){
            LOGGER.error("Error: Movement already exists for these player");
            throw new ContentAlreadyExistsException("Movement already exists for these player");
        }
    }

    public void clearAll() {
        MoveSingleton.clear();
    }
}
