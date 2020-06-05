package br.com.btg.jokenpo.services;

import br.com.btg.jokenpo.dto.MoveRequest;
import br.com.btg.jokenpo.dto.MoveResponse;
import br.com.btg.jokenpo.entity.Move;
import br.com.btg.jokenpo.entity.Player;
import br.com.btg.jokenpo.entity.mapper.MoveMapper;
import br.com.btg.jokenpo.enumeration.EnumMovement;
import br.com.btg.jokenpo.repository.MoveRepository;
import br.com.btg.jokenpo.repository.PlayerRepository;
import br.com.btg.jokenpo.services.exceptions.ContentAlreadyExistsException;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MoveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoveService.class);

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public MoveResponse insertMove(MoveRequest moveRequest){
        if(Objects.isNull(moveRequest)
                || StringUtils.isEmpty(moveRequest.getPlayerName())
                || StringUtils.isEmpty(moveRequest.getMovement())){
            LOGGER.error("Invalid movement");
            throw new CustomException("Invalid Movement", "Parameter is null");
        }
        LOGGER.debug("Move: {}", moveRequest);

        //Get the player associated with the move
        Player player = playerRepository.findByName(moveRequest.getPlayerName());

        //Checks if the player already has a move
        checkIfAlreadyMoved(player);

        //Get the movement
        EnumMovement movement = EnumMovement.getEnumMovementByName(moveRequest.getMovement());

        if(Objects.isNull(movement)){
            LOGGER.error("Movement not found");
            throw new ObjectNotFoundException("Movement not found");
        }

        //Save move
        Move move = moveRepository.saveMovement(new Move(player, movement));

        //Convert Move (entity) to response
        return MoveMapper.entityToResponse(move);
    }

    public List<MoveResponse> findAll(){
        LOGGER.debug("Finding all movements");
        List<Move> movesList = this.moveRepository.findAll();
        List<MoveResponse> movesResponse = new ArrayList<>();
        movesList.forEach(element -> movesResponse.add(MoveMapper.entityToResponse(element)));
        LOGGER.debug("Movements searched");
        return movesResponse;
    }

    private void checkIfAlreadyMoved(Player player){
        long count = moveRepository.findAll().stream()
                .filter(element ->
                        (element.getPlayer().getPlayerName()).compareToIgnoreCase(player.getPlayerName()) == 0)
                .count();
        if(count > 0){
            LOGGER.error("Error: Movement already exists for these player");
            throw new ContentAlreadyExistsException("Movement already exists for these player");
        }
    }
}
