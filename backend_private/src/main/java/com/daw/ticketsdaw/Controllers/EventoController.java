package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Services.EventosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    EventosService eventosService;

    @GetMapping({"/", ""})
    public String show(ModelMap modelMap){
        modelMap.addAttribute("eventos", eventosService.read());
        return "eventos";
    }

    @GetMapping({"crea"})
    public String showForm(){
        return "eventos-form";
    }
}
