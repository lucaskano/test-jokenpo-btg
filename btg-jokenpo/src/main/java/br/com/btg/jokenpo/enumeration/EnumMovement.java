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
        STONE.setWeaknesses(asList(SPOCK, PAPER));
        LIZARD.setWeaknesses(asList(SCISSORS, STONE));
        SPOCK.setWeaknesses(asList(LIZARD, PAPER));
        SCISSORS.setWeaknesses(asList(SPOCK, STONE));
        PAPER.setWeaknesses(asList(SCISSORS, LIZARD));
    }

    private String name;
    private List<EnumMovement> weaknesses;

    EnumMovement(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EnumMovement> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<EnumMovement> weaknesses) {
        this.weaknesses = weaknesses;
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
