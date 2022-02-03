package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salas-api")
public class SalaRestController {

    @Autowired
    private SalaService salaService;

    @GetMapping("{id}")
    public Sala getSalaAsJSON(@PathVariable int id){
        return salaService.read(id);
    }

}
