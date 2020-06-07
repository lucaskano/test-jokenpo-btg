package br.com.btg.jokenpo.services.implementation;

import br.com.btg.jokenpo.dto.JokenpoResponse;
import br.com.btg.jokenpo.dto.MoveResponse;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.enumeration.EnumMovement;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import br.com.btg.jokenpo.singletons.MoveSingleton;
import br.com.btg.jokenpo.singletons.PlayerSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JokenpoServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(JokenpoServiceImpl.class);

    @Autowired
    private MoveServiceImpl moveServiceImpl;

    @Autowired
    private PlayerServiceImpl playerServiceImpl;

    public JokenpoServiceImpl(){

    }

    public JokenpoResponse play(){
        checkRequeriments();
        List<String> winners = new ArrayList<>();
        LOGGER.debug("Generating the match result");

        moveServiceImpl.getAll().forEach(element ->{
            try{
                if(checkForAWinner(element.getMovement().getWeaknesses())){
                    winners.add(element.getPlayer().getPlayerName());
                }
            }catch(ObjectNotFoundException e){
                LOGGER.error("Error trying to find winners - Player Name : {} - Error Message : {}",
                        element.getPlayer().getPlayerName(), e.getMessage());
            }
        });
        LOGGER.debug("Result generated");

        JokenpoResponse matchResult = new JokenpoResponse(getWinnersMessage(winners),
                getHistoryFromMoves(moveServiceImpl.getAll()));
        LOGGER.debug("Winners message formatted");

        LOGGER.debug("Erasing movements data");
        MoveSingleton.clear();

        LOGGER.debug("Round finished");
        return matchResult;
    }

    public List<PlayerResponse> clear(){
        LOGGER.debug("Clearing all data");
        MoveSingleton.clear();
        PlayerSingleton.clear();
        LOGGER.debug("Erased data");
        return this.playerServiceImpl.findAll();
    }

    private void checkRequeriments(){
        if(playerServiceImpl.findAll().isEmpty()){
            throw new ObjectNotFoundException("There are no registered players");
        }else if(playerServiceImpl.findAll().size() <= 1){
            throw new ObjectNotFoundException("There are players who have not yet chosen");
        }else if(moveServiceImpl.getAll().size() <= 1){
            throw new ObjectNotFoundException("There area not enough movements");
        }else if(moveServiceImpl.getAll().size() != playerServiceImpl.findAll().size()){
            throw new ObjectNotFoundException("There are players without moves");
        }
    }

    private Boolean checkForAWinner(List<EnumMovement> weaknesses) {
        for (EnumMovement enumMovement : weaknesses) {
            LOGGER.debug("Checking weaknesses : {}", enumMovement.getName());
            for(MoveResponse response : this.moveServiceImpl.getAll()){
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
            message = new StringBuilder("NOBODY WON!");
        } else if(winners.size() == 1) {
            message = new StringBuilder(winners.get(0).toUpperCase().trim() + " IS THE WINNER!");
        } else {
            message = new StringBuilder("THE WINNERS");
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
