package br.com.btg.jokenpo.services;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.PlayerResponse;
import br.com.btg.jokenpo.entities.PlayerEntity;

import java.util.List;

public interface PlayerService {

    public List<PlayerResponse> findAll();
    public PlayerEntity findByName(String name);
    public PlayerResponse save(PlayerRequest playerRequest);
    public List<PlayerResponse> deleteByName(String name);

}
