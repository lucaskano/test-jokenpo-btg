package br.com.btg.jokenpo.services;

import br.com.btg.jokenpo.dto.MoveRequest;
import br.com.btg.jokenpo.dto.MoveResponse;

import java.util.List;

public interface MoveService {

    public MoveResponse insertMove(MoveRequest moveRequest);

    public List<MoveResponse> getAll();

    public List<MoveResponse> deleteByPlayerName(String playerName);
}
