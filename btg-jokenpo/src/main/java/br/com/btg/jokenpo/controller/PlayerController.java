package br.com.btg.jokenpo.controller;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.exception.JokenpoException;
import br.com.btg.jokenpo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    public PlayerController(){

    }

    @GetMapping
    public ResponseEntity<Object> getAll() throws JokenpoException{
        return ResponseEntity.ok().body(playerService.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> insertPlayer(@Valid @RequestBody PlayerRequest playerRequest) throws JokenpoException{
        assert playerRequest != null;
        playerService.save(playerRequest);
        return ResponseEntity.ok().body("The player " + playerRequest.getName() + " inserted");
    }
}
