package br.com.btg.jokenpo.controller;

import br.com.btg.jokenpo.dto.api.FormatResponse;
import br.com.btg.jokenpo.services.JokenpoService;
import br.com.btg.jokenpo.services.exceptions.CustomException;
import br.com.btg.jokenpo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/play")
public class JokenpoController {

    @Autowired
    private JokenpoService jokenpoService;

    @GetMapping
    public ResponseEntity<Object> play() throws ValidationException {
        return ResponseEntity.ok(new FormatResponse<>(jokenpoService.play()));
    }
}
