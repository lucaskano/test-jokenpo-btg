package br.com.btg.jokenpo.singletons;

import br.com.btg.jokenpo.entities.MoveEntity;

import java.util.ArrayList;
import java.util.List;

public class MoveSingleton {

    private static List<MoveEntity> moveEntityInstance;
    private static final String INFO = "Movement Singleton Instance";

    private MoveSingleton(){

    }

    public static List<MoveEntity> getInstance(){
        if(moveEntityInstance == null){
            moveEntityInstance = new ArrayList<>();
        }
        return moveEntityInstance;
    }

    public String getINFO() {
        return INFO;
    }
}
