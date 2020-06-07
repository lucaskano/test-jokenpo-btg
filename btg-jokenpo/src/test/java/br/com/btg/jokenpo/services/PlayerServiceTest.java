package br.com.btg.jokenpo.services;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.entities.PlayerEntity;
import br.com.btg.jokenpo.services.exceptions.CustomException;
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
public class PlayerServiceTest {

    @Autowired
    private PlayerServiceImpl playerService;

    @Test
    public void successfullyRegisteredPlayer(){
        // Clear singleton data
        this.playerService.clearAll();

        // Insert just one player
        String expectedPlayerName = "PLAYER NAME";
        PlayerResponse playerResponse = this.playerService.save(new PlayerRequest(expectedPlayerName));
        Assert.assertEquals(expectedPlayerName, playerResponse.getPlayerName());
    }

    @Test(expected = CustomException.class)
    public void insertDuplicateRecordsToGenerateAnException(){
        // Clear singleton data
        this.playerService.clearAll();

        // Save the player name twice
        String playerNameDuplicated = "DUPLICATED NAME";
        this.playerService.save(new PlayerRequest(playerNameDuplicated));
        this.playerService.save(new PlayerRequest(playerNameDuplicated));
    }

    @Test
    public void multipleSuccessfulInsertions(){
        // Clear singleton data
        this.playerService.clearAll();

        // Insert many players
        List<String> playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        List<PlayerResponse> playerResponses = this.insertServeralDifferentPlayers(playerNames);

        // Assertments check
        int position = 0;
        for (String expectedName : playerNames) {
            Assert.assertEquals(expectedName, playerResponses.get(position).getPlayerName());
            position++;
        }
    }

    @Test
    public void getAllPlayersWithSucess(){
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        List<String> playerNames = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        List<PlayerResponse> playerResponses = this.insertServeralDifferentPlayers(playerNames);
        // Assertments check
        Assert.assertEquals(playerNames.size(), playerResponses.size());
        Assert.assertEquals(playerNames.size(), this.playerService.findAll().size());
        Assert.assertEquals(playerResponses.size(), this.playerService.findAll().size());
    }

    @Test
    public void getPlayerByNameSuccessfully(){
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        List<String> playerNames = new ArrayList<>(Arrays.asList("Player1", "Player2", "Player3", "Player4", "Player5"));
        List<PlayerResponse> playerResponses = this.insertServeralDifferentPlayers(playerNames);
        PlayerEntity entity = this.playerService.findByName("Player2");
        // Assertments check
        Assert.assertEquals("Player2", entity.getPlayerName());
    }

    @Test(expected = CustomException.class)
    public void getPlayerByNameWithException(){
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        List<String> playerNames = new ArrayList<>(Arrays.asList("Player1", "Player2", "Player3", "Player4", "Player5"));
        this.insertServeralDifferentPlayers(playerNames);
        this.playerService.findByName("Nonexistent");
    }

    @Test
    public void deleteByNameSuccessfully(){
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        List<String> playerNames = new ArrayList<>(Arrays.asList("Player1", "Player2", "Player3", "Player4", "Player5"));
        List<PlayerResponse> playerResponse = this.insertServeralDifferentPlayers(playerNames);
        int expected1 = playerNames.size()-1;
        int expected2 = playerResponse.size()-1;
        List<PlayerResponse> list = this.playerService.deleteByName("Player1");
        // Assertments check
        Assert.assertEquals(expected1, list.size());
        Assert.assertEquals(expected2, list.size());
    }

    @Test(expected = CustomException.class)
    public void deleteByNameWithException() throws Exception {
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        List<String> playerNames = new ArrayList<>(Arrays.asList("Player1", "Player2", "Player3", "Player4", "Player5"));
        this.insertServeralDifferentPlayers(playerNames);
        this.playerService.deleteByName("NONEXISTENT");
    }

    @Test
    public void clearAllSuccessfully(){
        // Clear singleton data
        this.playerService.clearAll();
        // Insert many players
        List<String> playerNames = new ArrayList<>(Arrays.asList("Player1", "Player2", "Player3", "Player4", "Player5"));
        List<PlayerResponse> playerResponse = this.insertServeralDifferentPlayers(playerNames);
        Assert.assertEquals(playerNames.size(), playerResponse.size());
        // Clear singleton data
        this.playerService.clearAll();
        // List update
        playerResponse = this.playerService.findAll();
        // Assertments check
        Assert.assertEquals(0, playerResponse.size());
    }

    private List<PlayerResponse> insertServeralDifferentPlayers(List<String> playerNames){
        List<PlayerResponse> list = new ArrayList<>();
        for(String name : playerNames){
            PlayerResponse playerResponse = this.playerService.save(new PlayerRequest(name));
            list.add(playerResponse);
        }
        return list;
    }

}
