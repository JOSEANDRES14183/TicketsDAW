package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.UsuarioDTO;
import com.daw.ticketsdaw.Entities.Organizador;
import com.daw.ticketsdaw.Entities.PropietarioSala;
import com.daw.ticketsdaw.Entities.Usuario;
import com.daw.ticketsdaw.Services.RecursoMediaService;
import com.daw.ticketsdaw.Services.UsuarioService;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RecursoMediaService recursoMediaService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/update")
    public String showUpdateForm(ModelMap modelMap, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelMap.addAttribute("usuario", usuario);
        modelMap.addAttribute("tipoUsuario", usuario.getClass() == PropietarioSala.class ? "propietario" : "organizador");
        return "user/form";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute UsuarioDTO usuarioDTO, HttpSession session, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()){
            return "redirect:/user/update?error=validation";
        }

        Usuario usuarioPrevState = usuarioService.getById(usuarioDTO.getId());
        Usuario usuario = modelMapper.map(usuarioDTO, PropietarioSala.class);

        if(usuarioPrevState.getClass()== Organizador.class){
            usuario = modelMapper.map(usuarioDTO, Organizador.class);
            if (usuarioDTO.getFotoPerfil().getOriginalFilename()==""){
                ((Organizador) usuario).setFotoPerfil(((Organizador) usuarioPrevState).getFotoPerfil());
            } else {
                ((Organizador) usuario).setFotoPerfil(recursoMediaService.createFromFile(usuarioDTO.getFotoPerfil()));
            }
        }

        usuario.setPasswordHash(usuarioPrevState.getPasswordHash());

        try {
            usuarioService.create(usuario);
        } catch (SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException) {
            return "redirect:/user/update?error=duplicate";
        }

        session.setAttribute("usuario", usuario);
        if (usuario.getClass() == PropietarioSala.class) {
            return "redirect:/salas";
        }
        return "redirect:/eventos";
    }

    @GetMapping("/change-password")
    public String showChangePassForm(HttpSession session, ModelMap modelMap){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        modelMap.addAttribute("tipoUsuario", usuario.getClass() == PropietarioSala.class ? "propietario" : "organizador");
        return "user/change-pass-form";
    }

    @PostMapping("/change-password")
    public String changePassword(HttpSession session, @RequestParam String prevPasswordHash, @RequestParam String passwordHash) throws SQLIntegrityConstraintViolationException {
        Usuario usuarioSession = (Usuario)session.getAttribute("usuario");
        if (passwordEncoder.matches(prevPasswordHash,usuarioSession.getPasswordHash())){
            Usuario usuario = usuarioService.getById(usuarioSession.getId());
            usuario.setPasswordHash(passwordEncoder.encode(passwordHash));
            usuarioService.create(usuario);
            if (usuario.getClass() == PropietarioSala.class) {
                return "redirect:/salas";
            }
            return "redirect:/eventos";
        }
        return "redirect:/user/change-password?error=password";
    }


    @GetMapping("/delete")
    public String deleteUser(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        usuarioService.delete(usuario);
        session.removeAttribute("usuario");
        return "redirect:/auth/login";
    }
}
