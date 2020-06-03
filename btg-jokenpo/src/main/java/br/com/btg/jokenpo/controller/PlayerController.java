package br.com.btg.jokenpo.controller;

import br.com.btg.jokenpo.dto.PlayerRequest;
import br.com.btg.jokenpo.exception.JokenpoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    @GetMapping
    public ResponseEntity<Object> getAll() throws JokenpoException{
        return ResponseEntity.ok().body(new PlayerRequest("Lucas Kanô"));
    }
}
