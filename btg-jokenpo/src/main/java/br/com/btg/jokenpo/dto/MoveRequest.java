package br.com.btg.jokenpo.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class MoveRequest {

    @Size(min = 1)
    @NotBlank(message = "Player is required")
    private String playerName;

    @Size(min = 1)
    @NotBlank(message = "Movement is required")
    private String movement;

    public MoveRequest(){

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveRequest that = (MoveRequest) o;
        return Objects.equals(playerName, that.playerName) &&
                Objects.equals(movement, that.movement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName, movement);
    }

    @Override
    public String toString() {
        return "MoveRequest{" +
                "player='" + playerName + '\'' +
                ", movement='" + movement + '\'' +
                '}';
    }
}
