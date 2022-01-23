package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Services.CiudadService;
import com.daw.ticketsdaw.Services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        Sala sala = salaService.read(salaId);
        modelMap.addAttribute("sala",sala);
        modelMap.addAttribute("butacas",salaService.getButacasJson(sala));
        return "salas/show";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable("id") int salaId){
        salaService.delete(salaService.read(salaId));
        return "redirect:/salas";
    }

    @GetMapping({"create"})
    public String create(ModelMap modelMap){
        Sala sala = new Sala();
        sala.setAforoMax(1);
        modelMap.addAttribute("sala",sala);
        modelMap.addAttribute("ciudades",ciudadService.read());
        return "salas/form";
    }

    @GetMapping({"{id}/update"})
        public String update(@PathVariable("id") Integer id, ModelMap modelMap){
        modelMap.addAttribute("sala",salaService.read(id));
        modelMap.addAttribute("ciudades",ciudadService.read());
        return "salas/form";
    }

    @PostMapping({"/",""})
    public String saveSala(@ModelAttribute @Valid Sala sala, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/salas/create";
        }
        salaService.create(sala);
        return "redirect:/salas";
    }

}
