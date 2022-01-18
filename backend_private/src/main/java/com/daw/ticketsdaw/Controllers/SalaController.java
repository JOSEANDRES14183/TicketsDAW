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
        modelMap.addAttribute("sala",salaService.read(salaId));
        return "salas/show";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable("id") int salaId){
        salaService.delete(salaService.read(salaId));
        return "redirect:/salas";
    }

    @GetMapping({"form"})
    public String showForm(ModelMap modelMap, @RequestParam(required = false, name = "id") int id){
        modelMap.addAttribute("ciudades",ciudadService.read());
        Sala sala = new Sala();
        if(salaService.checkById(id)){
            sala = salaService.read(id);
        }
        modelMap.addAttribute("sala",sala);
        return "salas/form";
    }

    @PostMapping({"form"})
    public String processForm(@ModelAttribute @Valid Sala sala, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "redirect:/salas/form";
        }
        salaService.create(sala);
        return "redirect:/salas";
    }

}
