package br.com.btg.jokenpo.dto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class PlayerResponse {

    @NotBlank(message = "Player name is required")
    private String playerName;

    public PlayerResponse(){

    }

    public PlayerResponse(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerResponse that = (PlayerResponse) o;
        return Objects.equals(playerName, that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }

    @Override
    public String toString() {
        return "PlayerResponse{" +
                "name='" + playerName + '\'' +
                '}';
    }
}
