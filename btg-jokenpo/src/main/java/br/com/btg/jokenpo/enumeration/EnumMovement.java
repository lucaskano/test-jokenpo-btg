package br.com.btg.jokenpo.enumeration;

import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public enum EnumMovement {

    STONE("STONE"),
    LIZARD("LIZARD"),
    SPOCK("SPOCK"),
    SCISSORS("SCISSORS"),
    PAPER("PAPER");

    static {
        STONE.setLoses(asList(SPOCK, PAPER));
        LIZARD.setLoses(asList(SCISSORS, STONE));
        SPOCK.setLoses(asList(LIZARD, PAPER));
        SCISSORS.setLoses(asList(SPOCK, STONE));
        PAPER.setLoses(asList(SCISSORS, LIZARD));
    }

    private String name;
    private List<EnumMovement> loses;

    EnumMovement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EnumMovement> getLoses() {
        return loses;
    }

    public void setLoses(List<EnumMovement> loses) {
        this.loses = loses;
    }

    public static EnumMovement getEnumMovementByName(String name){
        for(EnumMovement element : Arrays.asList(EnumMovement.values())){
            if(name.equals(element.getName())){
                return element;
            }
        }
        throw new ObjectNotFoundException("Movement not found");
    }
}
