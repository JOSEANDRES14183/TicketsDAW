package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Services.CiudadService;
import com.daw.ticketsdaw.Services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @Autowired
    private CiudadService ciudadService;

    @GetMapping({"/",""})
    public String index(ModelMap modelMap){
        modelMap.addAttribute("salas",salaService.read());
        return "salas/index";
    }

    @GetMapping("{id}")
    public String show(ModelMap modelMap, @PathVariable("id") int salaId){
        modelMap.addAttribute("sala",salaService.read(salaId));
        return "salas/show";
    }

    @GetMapping("{id}/delete")
    public String delete(ModelMap modelMap, @PathVariable("id") int salaId){
        salaService.delete(salaService.read(salaId));
        return "redirect:/salas";
    }

    @GetMapping({"crea"})
    public String create(ModelMap modelMap){
        modelMap.addAttribute("sala", new Sala());
        modelMap.addAttribute("ciudades", ciudadService.read());
        return "salas/create";
    }

    @PostMapping({"crea"})
    public String store(ModelMap modelMap, @ModelAttribute("sala") Sala sala){
        salaService.create(sala);
        return "redirect:/salas";
    }

}
