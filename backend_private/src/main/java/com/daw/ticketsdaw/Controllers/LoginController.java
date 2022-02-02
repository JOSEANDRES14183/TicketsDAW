package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.OrganizadorDTO;
import com.daw.ticketsdaw.Entities.Organizador;
import com.daw.ticketsdaw.Entities.PropietarioSala;
import com.daw.ticketsdaw.Entities.Usuario;
import com.daw.ticketsdaw.Services.NormasEventoService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    RecursoMediaService mediaService;
    @Autowired
    NormasEventoService normasService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/login")
    public String showLogin(ModelMap modelMap, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario!=null){
            modelMap.addAttribute("welcome","Welcome " + usuario.getNombreUsuario());
        }
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, ModelMap modelMap){
        Usuario usuario = usuarioService.getByNombreUsuario(username);
        if(passwordEncoder.matches(password,usuario.getPasswordHash())){
            request.getSession().setAttribute("usuario",usuario);
            modelMap.addAttribute("welcome","Welcome " + usuario.getNombreUsuario());
        }
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
    public String savePropietario(@Valid @ModelAttribute PropietarioSala propietarioSala, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "redirect:/auth/register/propietario?error=validation";
        }
        propietarioSala.setPasswordHash(passwordEncoder.encode(propietarioSala.getPasswordHash()));
        usuarioService.create(propietarioSala);
        return "redirect:/salas";
    }

    @PostMapping("/register/organizador")
    public String saveOrganizador(@Valid @ModelAttribute OrganizadorDTO organizadorDTO, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()){
            return "redirect:/auth/register/organizador?error=validation";
        }

        Organizador organizador = modelMapper.map(organizadorDTO, Organizador.class);

        organizador.setPasswordHash(passwordEncoder.encode(organizadorDTO.getPasswordHash()));
        organizador.setFotoPerfil(mediaService.createFromFile(organizadorDTO.getFotoPerfil()));

        usuarioService.create(organizador);

        return "redirect:/eventos";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/auth/login";
    }



}
