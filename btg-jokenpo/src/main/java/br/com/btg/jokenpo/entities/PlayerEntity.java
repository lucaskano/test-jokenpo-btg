package br.com.btg.jokenpo.entities;

import javax.persistence.Entity;
import java.util.Objects;

/**
 *
 * Attention: Generation of ID for the entity will not be used as the application will not use a database,
 * for this reason it was chosen not to insert the "id" attribute.
 *
 * */

@Entity
public class PlayerEntity {

    private String playerName;

    public PlayerEntity(){

    }

    public PlayerEntity(String playerName) {
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
        PlayerEntity playerEntity = (PlayerEntity) o;
        return Objects.equals(playerName, playerEntity.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerName);
    }
}
