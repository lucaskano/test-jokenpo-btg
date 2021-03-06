package br.com.btg.jokenpo.controller;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.dto.api.FormatResponse;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.implementation.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    @Autowired
    private PlayerServiceImpl playerServiceImpl;

    public PlayerController(){

    }

    @GetMapping
    public ResponseEntity<Object> getAll() throws CustomException {
        return ResponseEntity.ok(new FormatResponse<>(playerServiceImpl.findAll()));
    }

    @GetMapping(path = "/{playerName}")
    public ResponseEntity<Object> getByName(@PathVariable("playerName") String playerName) throws CustomException {
        return ResponseEntity.ok(new FormatResponse<>(playerServiceImpl.findByName(playerName)));
    }

    @PostMapping
    public ResponseEntity<Object> insertPlayer(@Valid @RequestBody PlayerRequest playerRequest)
            throws CustomException {
        return ResponseEntity.ok(
                new FormatResponse<>(playerServiceImpl.save(playerRequest)));
    }

    @DeleteMapping
    public ResponseEntity<Object> deletePlayer(@PathParam("name") String name) throws CustomException {
        return ResponseEntity.ok(playerServiceImpl.deleteByName(name));
    }

}
