package br.com.btg.jokenpo.repository;


import br.com.btg.jokenpo.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository {

    List<Player> findAll();

    Player findByName(String name);
}
