package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.Entities.Organizador;
import com.daw.ticketsdaw.Entities.PropietarioSala;
import com.daw.ticketsdaw.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

//    @Autowired
//    private PropietarioService propietarioService;
//    @Autowired
//    private OrganizadorService organizadorService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @PostMapping("/register/propietario")
    public String savePropietario(@Valid @ModelAttribute PropietarioSala propietarioSala, ModelMap modelMap){
        propietarioSala.setPasswordHash(passwordEncoder.encode(propietarioSala.getPasswordHash()));
        usuarioService.create(propietarioSala);
        return "redirect:/salas";
    }

    @PostMapping("/register/organizador")
    public String saveOrganizador(@Valid @ModelAttribute Organizador organizador, ModelMap modelMap){
        organizador.setPasswordHash(passwordEncoder.encode(organizador.getPasswordHash()));
        usuarioService.create(organizador);
        return "redirect:/eventos";
    }



}
