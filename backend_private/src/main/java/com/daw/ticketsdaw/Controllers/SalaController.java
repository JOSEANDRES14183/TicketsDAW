package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Repositories.SalaRepository;
import com.daw.ticketsdaw.Services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    protected SalaService salaService;

    @GetMapping({"/"})
    public String show(ModelMap modelMap){
        modelMap.addAttribute("salas",salaService.read());
        return "salas";
    }

    @GetMapping({"crea"})
    public String upate(ModelMap modelMap){
        return "salas-form";
    }

}
