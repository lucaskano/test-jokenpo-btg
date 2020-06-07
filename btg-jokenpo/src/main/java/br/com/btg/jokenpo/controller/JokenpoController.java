package br.com.btg.jokenpo.controller;

import br.com.btg.jokenpo.dto.api.FormatResponse;
import br.com.btg.jokenpo.services.implementation.JokenpoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/play")
@CrossOrigin(origins = "*")
public class JokenpoController {

    @Autowired
    private JokenpoServiceImpl jokenpoServiceImpl;

    @GetMapping
    public ResponseEntity<Object> play() {
        return ResponseEntity.ok(new FormatResponse<>(jokenpoServiceImpl.play()));
    }

    @DeleteMapping
    public ResponseEntity<Object> restartGame(){
        return ResponseEntity.ok(new FormatResponse<>(jokenpoServiceImpl.clear()));
    }

}
