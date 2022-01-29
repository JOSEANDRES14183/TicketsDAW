package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Entities.Organizador;
import com.daw.ticketsdaw.Entities.PropietarioSala;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLogin(){
        return "login/login";
    }

    @PostMapping("/login")
    public String login(){
        return "login/login";
    }

    @GetMapping("/register/propietario")
    public String showRegisterPropietario(ModelMap modelMap){
        modelMap.addAttribute("propietario", new PropietarioSala());
        return "login/register-propietario";
    }

    @GetMapping("/register/organizador")
    public String showRegisterOrganizador(ModelMap modelMap){
        modelMap.addAttribute("organizador", new Organizador());
        return "login/register-organizador";
    }



}
