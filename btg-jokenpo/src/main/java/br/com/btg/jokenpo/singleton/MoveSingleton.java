package br.com.btg.jokenpo.singleton;

import br.com.btg.jokenpo.entity.Move;

import java.util.ArrayList;
import java.util.List;

public class MoveSingleton {

    private static List<Move> MOVE_INSTANCE;
    private static final String INFO = "Movement Singleton Instance";

    private MoveSingleton(){

    }

    public static List<Move> getInstance(){
        if(MOVE_INSTANCE == null){
            MOVE_INSTANCE = new ArrayList<>();
        }
        return MOVE_INSTANCE;
    }

    public String getINFO() {
        return INFO;
    }
}
