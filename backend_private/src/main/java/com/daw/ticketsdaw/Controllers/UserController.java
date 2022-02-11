package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.UsuarioDTO;
import com.daw.ticketsdaw.Entities.PropietarioSala;
import com.daw.ticketsdaw.Entities.Usuario;
import com.daw.ticketsdaw.Services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/update")
    public String showUpdateForm(ModelMap modelMap, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelMap.addAttribute("usuario",usuario);
        modelMap.addAttribute("tipoUsuario",usuario.getClass()== PropietarioSala.class ? "propietario" : "organizador");
        return "user/form";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute UsuarioDTO usuarioDTO){
        Usuario usuario = modelMapper.map(usuarioDTO, PropietarioSala.class);
        usuarioService.create(usuario);
        if (usuario.getClass() == PropietarioSala.class){
            return "redirect:/salas";
        }
        return "redirect:/eventos";
    }

    @GetMapping("/delete")
    public String deleteUser(HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        usuarioService.delete(usuario);
        if (usuario.getClass() == PropietarioSala.class){
            return "redirect:/salas";
        }
        return "redirect:/eventos";
    }
}
