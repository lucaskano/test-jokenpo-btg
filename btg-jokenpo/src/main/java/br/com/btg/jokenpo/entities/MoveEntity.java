package br.com.btg.jokenpo.entities;

import br.com.btg.jokenpo.enumeration.EnumMovement;

import javax.persistence.Entity;

/**
 *
 * Attention: Generation of ID for the entity will not be used as the application will not use a database,
 * for this reason it was chosen not to insert the "id" attribute.
 *
 * */

@Entity
public class MoveEntity {

    private PlayerEntity playerEntity;
    private EnumMovement enumMovement;

    public MoveEntity(PlayerEntity playerEntity, EnumMovement enumMovement) {
        this.playerEntity = playerEntity;
        this.enumMovement = enumMovement;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public void setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    public EnumMovement getEnumMovement() {
        return enumMovement;
    }

    public void setEnumMovement(EnumMovement enumMovement) {
        this.enumMovement = enumMovement;
    }
}
