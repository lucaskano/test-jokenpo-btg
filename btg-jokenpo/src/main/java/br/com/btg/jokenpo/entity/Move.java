package br.com.btg.jokenpo.entity;

import br.com.btg.jokenpo.enumeration.EnumMovement;

import javax.persistence.Entity;

/**
 *
 * Attention: Generation of ID for the entity will not be used as the application will not use a database,
 * for this reason it was chosen not to insert the "id" attribute.
 *
 * */

@Entity
public class Move {

    private Player player;
    private EnumMovement enumMovement;

    public Move(Player player, EnumMovement enumMovement) {
        this.player = player;
        this.enumMovement = enumMovement;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public EnumMovement getEnumMovement() {
        return enumMovement;
    }

    public void setEnumMovement(EnumMovement enumMovement) {
        this.enumMovement = enumMovement;
    }
}
