package br.com.btg.jokenpo.singletons;

import br.com.btg.jokenpo.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayerSingleton {

    private static List<PlayerEntity> playerEntityInstance;
    private static final String INFO = "Player Singleton Instance";

    private PlayerSingleton(){

    }

    public static List<PlayerEntity> getInstance(){
        if(playerEntityInstance == null){
            playerEntityInstance = new ArrayList<>();
        }
        return playerEntityInstance;
    }

    public static List<PlayerEntity> clear(){
        playerEntityInstance = new ArrayList<>();
        return getInstance();
    }

    public String getInfo(){
        return INFO;
    }
}
