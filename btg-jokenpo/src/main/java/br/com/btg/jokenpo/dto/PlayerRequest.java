package br.com.btg.jokenpo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class PlayerRequest {

    @NotBlank(message = "Player name is required" )
    @JsonProperty(value = "playerName")
    @Length(min = 4, max = 120, message = "The length must be between 4 and 120 characters")
    private String playerName;

    public PlayerRequest(){

    }

    public PlayerRequest(String playerName) {
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
        PlayerRequest that = (PlayerRequest) o;
        return Objects.equals(playerName, that.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }

    @Override
    public String toString() {
        return "PlayerRequest{" +
                "name='" + playerName + '\'' +
                '}';
    }
}
