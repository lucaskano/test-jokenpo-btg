package br.com.btg.jokenpo.singleton;

import br.com.btg.jokenpo.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerSingleton {

    private static List<Player> PLAYER_INSTANCE;
    private static String INFO = "Player Singleton Instance";

    private PlayerSingleton(){

    }

    public static List<Player> getInstance(){
        if(PLAYER_INSTANCE == null){
            PLAYER_INSTANCE = new ArrayList<Player>();
        }
        return PLAYER_INSTANCE;
    }

    public String getInfo(){
        return this.INFO;
    }
}
