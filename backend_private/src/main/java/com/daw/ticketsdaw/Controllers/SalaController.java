package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Entities.Ciudad;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Services.CiudadService;
import com.daw.ticketsdaw.Services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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
    @Transactional
    public String delete(ModelMap modelMap, @PathVariable("id") int salaId){
        salaService.delete(salaService.read(salaId));
        return "redirect:/salas";
    }

    @GetMapping({"create"})
    public String create(ModelMap modelMap, Sala sala){
        sala.setAforoMax(1);
        modelMap.addAttribute("sala", sala);
        modelMap.addAttribute("ciudades", ciudadService.read());
        return "salas/create";
    }

    @PostMapping({"create"})
    @Transactional
    public String store(@ModelAttribute @Valid Sala sala, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/salas/create";
        }
        salaService.create(sala);
        return "redirect:/salas";
    }

    @GetMapping({"{id}/update"})
    public String update(ModelMap modelMap, @PathVariable("id") int salaId){
        modelMap.addAttribute("sala", salaService.read(salaId));
        modelMap.addAttribute("ciudades",ciudadService.read());
        modelMap.addAttribute("accion","update");
        return "salas/create";
    }

    @PostMapping({"{id}/update"})
    @Transactional
    public String put(@ModelAttribute Sala sala){
        salaService.update(sala);
        return "redirect:/salas";
    }

}
