package br.com.btg.jokenpo.services;

import br.com.btg.jokenpo.dto.JokenpoResponse;
import br.com.btg.jokenpo.dto.MoveResponse;
import br.com.btg.jokenpo.enumeration.EnumMovement;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import br.com.btg.jokenpo.singletons.MoveSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JokenpoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JokenpoService.class);

    @Autowired
    private MoveService moveService;

    @Autowired
    private PlayerService playerService;

    public JokenpoService(){

    }

    public JokenpoResponse play() throws ValidationException {
        checkRequeriments();
        List<String> winners = new ArrayList<>();
        LOGGER.debug("Generating the match result");

        moveService.getAll().forEach(element ->{
            try{
                if(checkForAWinner(element.getMovement().getWeaknesses())){
                    winners.add(element.getPlayer().getPlayerName());
                }
            }catch(CustomException e){
                LOGGER.error("Error trying to find winners - Player Name : {} - Error Message : {}",
                        element.getPlayer().getPlayerName(), e.getMessage());
            }
        });
        LOGGER.debug("Result generated");

        JokenpoResponse matchResult = new JokenpoResponse(getWinnersMessage(winners),
                getHistoryFromMoves(moveService.getAll()));
        LOGGER.debug("Winners message formatted");

        LOGGER.debug("Erasing movements data");
        MoveSingleton.clear();

        LOGGER.debug("Round finished");
        return matchResult;
    }

    private void checkRequeriments(){
        if(playerService.findAll().isEmpty()){
            throw new ObjectNotFoundException("There are no registered players");
        }else if(playerService.findAll().size() <= 1){
            throw new CustomException("There are not enough players", "Insufficient players");
        }else if(moveService.getAll().size() <= 1){
            throw new CustomException("There area not enough movements", "Insufficient movements");
        }else if(moveService.getAll().size() != playerService.findAll().size()){
            throw new CustomException("There are players without moves", "Players without moves");
        }
    }

    private Boolean checkForAWinner(List<EnumMovement> weaknesses) {
        for (EnumMovement enumMovement : weaknesses) {
            LOGGER.debug("Checking weaknesses : {}", enumMovement.getName());
            for(MoveResponse response : this.moveService.getAll()){
                if(response.getMovement().getName().compareTo(enumMovement.getName()) == 0){
                    LOGGER.debug("LOSER - Lost to {} - {}", response.getPlayer().getPlayerName(), enumMovement.getName());
                    return false;
                }
            }
        }
        LOGGER.debug("WINNER DETECTED");
        return true;
    }

    private String getWinnersMessage(List<String> winners){
        new StringBuilder();
        StringBuilder message;
        if(winners.isEmpty()){
            message = new StringBuilder("Nobody won");
        } else if(winners.size() == 1) {
            message = new StringBuilder(winners.get(0).toUpperCase().trim() + " is the winner");
        } else {
            message = new StringBuilder("The Winners");
            int counter = 0;
            for(String name : winners){
                counter++;
                if(counter == winners.size()){
                    message.append(name);
                } else {
                    message.append(name).append("/");
                }
            }
        }
        return message.toString();
    }

    private List<String> getHistoryFromMoves(List<MoveResponse> list) {
        List<String> result = new ArrayList<>();
        for(MoveResponse resp : list){
            String message = resp.getPlayer().getPlayerName() + " (" + resp.getMovement().getName() + ")";
            result.add(message);
        }
        return result;
    }

}
