package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.OrganizadorDTO;
import com.daw.ticketsdaw.EmailSenders.UserConfirmationSender;
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

import javax.mail.MessagingException;
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
    @Autowired
    private UserConfirmationSender userConfirmationSender;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/login")
    public String showLogin(){
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, ModelMap modelMap) throws MessagingException {
        Usuario usuario = usuarioService.getByNombreUsuario(username);
        if (usuario!=null && passwordEncoder.matches(password, usuario.getPasswordHash())) {
            if (!usuario.isEstaValidado()){
                modelMap.addAttribute("usuario", usuario);
                request.getSession().setAttribute("usuario");
                sendVerificationMail(usuario.getEmail(), usuario.getId());
                return "redirect:/auth/pending-verification/"+usuario.getId();
            }
                request.getSession().setAttribute("usuario", usuario);
                if (usuario.getClass() == PropietarioSala.class){
                    return "redirect:/salas";
                }
                if (usuario.getClass() == Organizador.class){
                    return "redirect:/eventos";
                }
        }
        return "redirect:/auth/login?error=password";
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
    public String savePropietario(@Valid @ModelAttribute PropietarioSala propietarioSala, BindingResult bindingResult, ModelMap modelMap) throws MessagingException {
        if (bindingResult.hasErrors()){
            return "redirect:/auth/register/propietario?error=validation";
        }
        propietarioSala.setPasswordHash(passwordEncoder.encode(propietarioSala.getPasswordHash()));

        usuarioService.create(propietarioSala);
        modelMap.addAttribute("usuario", propietarioSala);
        sendVerificationMail(propietarioSala.getEmail(), propietarioSala.getId());
        return "login/verify-user";
    }

    @PostMapping("/register/organizador")
    public String saveOrganizador(@Valid @ModelAttribute OrganizadorDTO organizadorDTO, BindingResult bindingResult, ModelMap modelMap) throws IOException, MessagingException {
        if (bindingResult.hasErrors()){
            return "redirect:/auth/register/organizador?error=validation";
        }

        Organizador organizador = modelMapper.map(organizadorDTO, Organizador.class);

        organizador.setPasswordHash(passwordEncoder.encode(organizadorDTO.getPasswordHash()));
        organizador.setFotoPerfil(mediaService.createFromFile(organizadorDTO.getFotoPerfil()));

        usuarioService.create(organizador);
        modelMap.addAttribute("usuario", organizador);
        sendVerificationMail(organizador.getEmail(), organizador.getId());
        return "login/verify-user";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/auth/login";
    }

    @GetMapping("/pending-verification/{id}")
    public String showPendingVerificaction(){
        return "login/verify-user";
    }

    @GetMapping("/verify/{id}")
    public String verifyUser(@PathVariable("id") int id){
        Usuario usuario = usuarioService.getById(id);
        usuario.setEstaValidado(true);
        usuarioService.create(usuario);
        return "redirect:/auth/login?verify=true";
    }

    private void sendVerificationMail(String mail, int userId) throws MessagingException {
        userConfirmationSender.sendMessage(mail, String.valueOf(userId));
    }



}
