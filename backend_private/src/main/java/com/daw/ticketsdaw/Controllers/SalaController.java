package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    protected SalaService salaService;

    @GetMapping({"/",""})
    public String index(ModelMap modelMap){
        modelMap.addAttribute("salas",salaService.read());
        List<Sala> asad = salaService.read();
        return "salas/index";
    }

    @GetMapping("{id}")
    public String show(ModelMap modelMap, @PathVariable("id") int salaId){
        modelMap.addAttribute("sala",salaService.read(salaId));
        return "salas/show";
    }

    @GetMapping({"crea"})
    public String store(){
        return "salas/create";
    }

}
