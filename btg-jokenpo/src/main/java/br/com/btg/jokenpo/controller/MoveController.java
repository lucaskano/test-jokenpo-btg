package br.com.btg.jokenpo.controller;

import br.com.btg.jokenpo.dto.MoveRequest;
import br.com.btg.jokenpo.dto.api.FormatResponse;
import br.com.btg.jokenpo.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/moves")
@CrossOrigin(origins = "*")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @PostMapping
    public ResponseEntity<Object> insertMove(@Valid @RequestBody MoveRequest moveRequest){
        return ResponseEntity.ok(new FormatResponse<>(moveService.insertMove(moveRequest)));
    }

    @GetMapping
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(new FormatResponse<>(moveService.getAll()));
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@PathParam("playerName") String playerName){
        return ResponseEntity.ok(new FormatResponse<>(moveService.deleteByPlayerName(playerName)));
    }
}
