package br.com.btg.jokenpo.services;

import br.com.btg.jokenpo.dto.*;
import br.com.btg.jokenpo.enumeration.EnumMovement;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import br.com.btg.jokenpo.services.implementation.JokenpoServiceImpl;
import br.com.btg.jokenpo.services.implementation.MoveServiceImpl;
import br.com.btg.jokenpo.services.implementation.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class JokenpoServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private PlayerServiceImpl playerService;

    @Autowired
    private MoveServiceImpl moveService;

    @Autowired
    private JokenpoServiceImpl jokenpoService;

    @Before
    public void setup(){
        this.clearAllData();
    }

    @Test
    public void clearAllDataSuccessfully() {
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2", "Player3", "Player4", "Player5", "Player6"));
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.SPOCK.getName()),
                        new MoveRequest("Player2", EnumMovement.PAPER.getName()),
                        new MoveRequest("Player3", EnumMovement.SCISSORS.getName())
                )
        );
        Assert.assertNotEquals(0, this.playerService.findAll().size());
        Assert.assertNotEquals(0, this.moveService.getAll().size());
        this.jokenpoService.clear();
        Assert.assertEquals(0, this.playerService.findAll().size());
        Assert.assertEquals(0, this.moveService.getAll().size());
    }

    @Test
    public void paperVersusScissorsPlaying(){
        // Players insert
        this.insertSomePlayers(Arrays.asList("PLAYER1", "PLAYER2"));
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("PLAYER1", EnumMovement.PAPER.getName()),
                        new MoveRequest("PLAYER2", EnumMovement.SCISSORS.getName())
                )
        );

        JokenpoResponse response = this.jokenpoService.play();
        Assert.assertNotNull(response.getMatchResult());
        String expected = "PLAYER2 IS THE WINNER!".toUpperCase().trim();
        Assert.assertEquals(expected, response.getMatchResult());
    }

    @Test
    public void paperVersusScissorsVersusStonePlaying(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2", "Player3"));
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.PAPER.getName()),
                        new MoveRequest("Player2", EnumMovement.SCISSORS.getName()),
                        new MoveRequest("Player3", EnumMovement.STONE.getName())
                )
        );
        // Action
        JokenpoResponse response = this.jokenpoService.play();
        Assert.assertNotNull(response.getMatchResult());
        String expected = "NOBODY WON!".toUpperCase().trim();
        Assert.assertEquals(expected, response.getMatchResult());
    }

    @Test
    public void lizardVersusScissorsVersusPaperPlaying(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2", "Player3"));
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.LIZARD.getName()),
                        new MoveRequest("Player2", EnumMovement.SCISSORS.getName()),
                        new MoveRequest("Player3", EnumMovement.PAPER.getName())
                )
        );
        JokenpoResponse response = this.jokenpoService.play();
        Assert.assertNotNull(response.getMatchResult());
        String expected = "Player2 IS THE WINNER!".toUpperCase().trim();
        String notExpected = "NOBODY WON!".toUpperCase().trim();
        Assert.assertNotEquals(notExpected, response.getMatchResult());
        Assert.assertEquals(expected, response.getMatchResult());
    }

    @Test
    public void spockVersusPaperPlaying(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2"));
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.SPOCK.getName()),
                        new MoveRequest("Player2", EnumMovement.PAPER.getName())
                )
        );
        // Action
        JokenpoResponse response = this.jokenpoService.play();
        // Assertments check
        Assert.assertNotNull(response.getMatchResult());
        String expected = "Player2 IS THE WINNER!".toUpperCase().trim();
        String notExpected = "NOBODY WON!".toUpperCase().trim();
        Assert.assertNotEquals(notExpected, response.getMatchResult());
        Assert.assertEquals(expected, response.getMatchResult());
    }

    @Test
    public void lizardVersusScissorsPlaying(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2"));
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.SCISSORS.getName()),
                        new MoveRequest("Player2", EnumMovement.LIZARD.getName())
                )
        );
        // Action
        JokenpoResponse response = this.jokenpoService.play();
        Assert.assertNotNull(response.getMatchResult());
        String expected = "Player1 IS THE WINNER!".toUpperCase().trim();
        String notExpected = "NOBODY WON!".toUpperCase().trim();
        Assert.assertNotEquals(notExpected, response.getMatchResult());
        Assert.assertEquals(expected, response.getMatchResult());
    }

    @Test
    public void invalidMovementPlayingWithExpectException(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2"));

        // Error Expected
        thrown.expect(ObjectNotFoundException.class);
        thrown.expectMessage("Movement not found");

        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.SCISSORS.getName()),
                        new MoveRequest("Player2", "OTHER_MOVEMENT")
                )
        );
    }

    @Test
    public void someMovementsPossibilitiesWithSucess() {
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2", "Player3"));
        // First movements
        movementGroupWithDifferentMovements1();
        this.jokenpoService.play();
        // Second movements
        movementGroupWithDifferentMovements2();
        this.jokenpoService.play();
        // Third movements
        movementGroupWithTwoEqualsMovements();
        this.jokenpoService.play();
        // Fourth movements
        movementGroupWithAllEqualsMovements();
        this.jokenpoService.play();
    }

    @Test
    public void playingRemovingAndIncludingSomeMovements(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2"));
        // Movements insert
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.PAPER.getName()));
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.SCISSORS.getName()));
        // Action
        JokenpoResponse response = this.jokenpoService.play();
        Assert.assertEquals("PLAYER1 IS THE WINNER!", response.getMatchResult());
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.PAPER.getName()));
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.STONE.getName()));
        // Movement remove
        this.moveService.deleteByPlayerName("Player2");
        Assert.assertEquals(1, this.moveService.getAll().size());
        // Movement insert
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.SPOCK.getName()));
        // Action
        response = this.jokenpoService.play();
        Assert.assertEquals("PLAYER2 IS THE WINNER!", response.getMatchResult());
    }

    @Test
    public void playingRemovingAndIncludingSomePlayers1(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2"));
        // Movements insert
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.SCISSORS.getName()));
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.STONE.getName()));
        // Player remove
        this.playerService.deleteByName("Player1");
        // Expected exception
        thrown.expect(ObjectNotFoundException.class);
        // Action
        JokenpoResponse response = this.jokenpoService.play();
    }

    @Test
    public void playingRemovingAndIncludingSomePlayers2(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2"));
        // Movements insert
        this.moveService.insertMove(new MoveRequest("Player1", EnumMovement.SCISSORS.getName()));
        this.moveService.insertMove(new MoveRequest("Player2", EnumMovement.STONE.getName()));
        // Player remove
        this.playerService.deleteByName("Player1");
        // Player include
        this.playerService.save(new PlayerRequest("Player3"));
        // Movement insert
        this.moveService.insertMove(new MoveRequest("Player3", EnumMovement.PAPER.getName()));
        // Action
        JokenpoResponse response = this.jokenpoService.play();
        Assert.assertNotEquals(0, response.getHistory());
        Assert.assertEquals("PLAYER3 IS THE WINNER!", response.getMatchResult());
    }

    @Test
    public void historyAfterPlayedWithSucess(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2", "Player3"));
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.SCISSORS.getName()),
                        new MoveRequest("Player2", EnumMovement.LIZARD.getName()),
                        new MoveRequest("Player3", EnumMovement.STONE.getName())
                )
        );
        // Action
        JokenpoResponse response = this.jokenpoService.play();
        Assert.assertNotEquals(0, response.getHistory().size());
        Assert.assertEquals(3, response.getHistory().size());
    }

    @Test
    public void historyBeforePlayWithExpectException(){
        // Players insert
        insertSomePlayers(Arrays.asList("Player1", "Player2", "Player3", "Player4", "Player5"));
        // Expect Exception
        thrown.expect(ObjectNotFoundException.class);
        // Action
        JokenpoResponse response = this.jokenpoService.play();
    }

    private List<PlayerResponse> insertSomePlayers(List<String> playerNameList) {
        List<PlayerResponse> list = new ArrayList<>();
        playerNameList.stream()
                .forEach(playerName -> {
                            try {
                                list.add(this.playerService.save(new PlayerRequest(playerName)));
                            } catch (CustomException e){
                                e.printStackTrace();
                            }
                        }
                );
        return list;
    }

    private List<MoveResponse> insertSomeMovements(List<MoveRequest> movementList){
        List<MoveResponse> list = new ArrayList<>();
        for(MoveRequest movement : movementList)
            list.add(this.moveService.insertMove(movement));
        return list;
    }

    private void clearAllData() {
        this.playerService.clearAll();
        this.moveService.clearAll();
    }

    private void movementGroupWithDifferentMovements1(){
        // Movements cleared
        this.moveService.clearAll();
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.SCISSORS.getName()),
                        new MoveRequest("Player2", EnumMovement.LIZARD.getName()),
                        new MoveRequest("Player3", EnumMovement.STONE.getName())
                )
        );
    }

    private void movementGroupWithDifferentMovements2(){
        // Movements cleared
        this.moveService.clearAll();
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.STONE.getName()),
                        new MoveRequest("Player2", EnumMovement.PAPER.getName()),
                        new MoveRequest("Player3", EnumMovement.SPOCK.getName())
                )
        );
    }

    private void movementGroupWithAllEqualsMovements(){
        // Movements cleared
        this.moveService.clearAll();
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.LIZARD.getName()),
                        new MoveRequest("Player2", EnumMovement.LIZARD.getName()),
                        new MoveRequest("Player3", EnumMovement.LIZARD.getName())
                )
        );
    }

    private void movementGroupWithTwoEqualsMovements(){
        // Movements cleared
        this.moveService.clearAll();
        // Movements insert
        insertSomeMovements(
                Arrays.asList(
                        new MoveRequest("Player1", EnumMovement.PAPER.getName()),
                        new MoveRequest("Player2", EnumMovement.LIZARD.getName()),
                        new MoveRequest("Player3", EnumMovement.PAPER.getName())
                )
        );
    }

}