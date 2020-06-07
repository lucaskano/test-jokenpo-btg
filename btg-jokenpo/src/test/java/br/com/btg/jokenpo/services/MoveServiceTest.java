package br.com.btg.jokenpo.services;

import br.com.btg.jokenpo.dto.MoveRequest;
import br.com.btg.jokenpo.dto.MoveResponse;
import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.enumeration.EnumMovement;
import br.com.btg.jokenpo.services.exceptions.ContentAlreadyExistsException;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.implementation.MoveServiceImpl;
import br.com.btg.jokenpo.services.implementation.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MoveServiceTest {

    @Autowired
    private PlayerServiceImpl playerService;

    @Autowired
    private MoveServiceImpl moveService;

    @Test
    public void insertManyPlayersForTestSuccessfully() {
        // Clear singleton data
        this.playerService.clearAll();
        this.moveService.clearAll();
        // Insert players
        List<String> playerNames = new ArrayList<>(Arrays.asList("Player1", "Player2", "Player3", "Player4", "Player5", "Player6"));
        List<PlayerResponse> playerResponse = this.insertManyDifferentPlayers(playerNames);
        Assert.assertEquals(playerNames.size(), playerResponse.size());
    }

    @Test
    public void insertOneMovement(){
        this.insertManyPlayersForTestSuccessfully();
        int expected = 1;
        MoveResponse response = this.moveService.insertMove(new MoveRequest("Player1", "STONE"));
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getMovement());
        Assert.assertNotNull(response.getPlayer());
        Assert.assertEquals(expected, this.moveService.getAll().size());
    }

    @Test
    public void playersWithoutMovements(){
        this.insertManyPlayersForTestSuccessfully();
        int playersCounter = this.playerService.findAll().size();
        int movementsCounter = this.moveService.getAll().size();
        Assert.assertEquals(0, movementsCounter);
        Assert.assertNotEquals(0, playersCounter);
    }

    @Test(expected = ContentAlreadyExistsException.class)
    public void insertDuplicatedMovementForSamePlayerWithException(){
        this.insertManyPlayersForTestSuccessfully();
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.STONE.getName()));
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.LIZARD.getName()));
    }

    @Test(expected = ContentAlreadyExistsException.class)
    public void insertDuplicatedMovementForSamePlayerAndSameMovementWithException(){
        this.insertManyPlayersForTestSuccessfully();
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.STONE.getName()));
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.STONE.getName()));
    }

    @Test
    public void insertMovementForDifferentPlayersSuccessfully(){
        this.insertManyPlayersForTestSuccessfully();
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.STONE.getName()));
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.LIZARD.getName()));
        this.moveService.insertMove(new MoveRequest("Player3", EnumMovement.STONE.getName()));
        this.moveService.insertMove(new MoveRequest("Player4", EnumMovement.SCISSORS.getName()));
        this.moveService.insertMove(new MoveRequest("Player5", EnumMovement.PAPER.getName()));
        Assert.assertEquals(5, this.moveService.getAll().size());
    }

    @Test
    public void deleteOneMovementSuccessfully(){
        this.insertManyPlayersForTestSuccessfully();
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.SPOCK.getName()));
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.STONE.getName()));
        this.moveService.insertMove(new MoveRequest("Player3", EnumMovement.SCISSORS.getName()));
        int beforeCounter = this.moveService.getAll().size();
        this.moveService.deleteByPlayerName("Player2");
        Assert.assertEquals(beforeCounter-1, this.moveService.getAll().size());
    }

    @Test
    public void deleteOneMovementAfterInsertAnotherSuccessfully(){
        this.insertManyPlayersForTestSuccessfully();
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.SPOCK.getName()));
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.STONE.getName()));
        this.moveService.insertMove(new MoveRequest("Player3", EnumMovement.SCISSORS.getName()));
        this.moveService.deleteByPlayerName("Player2");
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.PAPER.getName()));
        Assert.assertEquals(3, this.moveService.getAll().size());
    }

    @Test
    public void clearAllMovementsSuccessfully(){
        this.insertManyPlayersForTestSuccessfully();
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.SPOCK.getName()));
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.STONE.getName()));
        this.moveService.insertMove(new MoveRequest("Player3", EnumMovement.SCISSORS.getName()));
        Assert.assertNotEquals(0, this.moveService.getAll().size());
        this.moveService.clearAll();
        Assert.assertEquals(0, this.moveService.getAll().size());
    }

    private List<PlayerResponse> insertManyDifferentPlayers(List<String> playerNames){
        List<PlayerResponse> list = new ArrayList<>();
        for(String name : playerNames){
            PlayerResponse playerResponse = this.playerService.save(new PlayerRequest(name));
            list.add(playerResponse);
        }
        return list;
    }

}
