package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.OrganizadorDTO;
import com.daw.ticketsdaw.DTOs.PropietarioSalaDTO;
import com.daw.ticketsdaw.EmailSenders.UserConfirmationSender;
import com.daw.ticketsdaw.Entities.Organizador;
import com.daw.ticketsdaw.Entities.PropietarioSala;
import com.daw.ticketsdaw.Entities.Usuario;
import com.daw.ticketsdaw.Services.NormasEventoService;
import com.daw.ticketsdaw.Services.RecursoMediaService;
import com.daw.ticketsdaw.Services.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;

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
        if (usuario!=null) {
            if (!usuario.isEstaValidado()){
                String token = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                        .setSubject(usuario.getNombreUsuario())
                        .signWith(SignatureAlgorithm.HS512,"hola").compact();

                sendVerificationMail(usuario.getEmail(), token);
                return "redirect:/auth/pending-verification/";
            }
            if (passwordEncoder.matches(password, usuario.getPasswordHash())) {
                request.getSession().setAttribute("usuario", usuario);
                if (usuario.getClass() == PropietarioSala.class){
                    return "redirect:/salas";
                }
                if (usuario.getClass() == Organizador.class){
                    return "redirect:/eventos";
                }
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
    public String savePropietario(@Valid @ModelAttribute PropietarioSalaDTO propietarioSalaDTO, BindingResult bindingResult, ModelMap modelMap) throws MessagingException, SQLIntegrityConstraintViolationException {
        if (bindingResult.hasErrors()){
            return "redirect:/auth/register/propietario?error=validation";
        }

        PropietarioSala propietarioSala = modelMapper.map(propietarioSalaDTO, PropietarioSala.class);

        propietarioSala.setPasswordHash(passwordEncoder.encode(propietarioSalaDTO.getPasswordHash()));

        usuarioService.create(propietarioSala);

        String token = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(propietarioSala.getNombreUsuario())
                .signWith(SignatureAlgorithm.HS512,"hola").compact();

        sendVerificationMail(propietarioSala.getEmail(), token);

        return "redirect:/auth/pending-verification";
    }

    @PostMapping("/register/organizador")
    public String saveOrganizador(@Valid @ModelAttribute OrganizadorDTO organizadorDTO, BindingResult bindingResult, ModelMap modelMap) throws IOException, MessagingException, SQLIntegrityConstraintViolationException {
        if (bindingResult.hasErrors()){
            return "redirect:/auth/register/organizador?error=validation";
        }

        Organizador organizador = modelMapper.map(organizadorDTO, Organizador.class);

        organizador.setPasswordHash(passwordEncoder.encode(organizadorDTO.getPasswordHash()));
        organizador.setFotoPerfil(mediaService.createFromFile(organizadorDTO.getFotoPerfil()));

        usuarioService.create(organizador);

        String token = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(organizador.getNombreUsuario())
                .signWith(SignatureAlgorithm.HS512,"hola").compact();

        sendVerificationMail(organizador.getEmail(), token);

        return "redirect:/auth/pending-verification";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/auth/login";
    }

    @GetMapping("/pending-verification")
    public String showPendingVerificaction(){
        return "login/verify-user";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) throws SQLIntegrityConstraintViolationException {
        Claims body = Jwts.parser()
                .setSigningKey("hola")
                .parseClaimsJws(token)
                .getBody();
        Usuario usuario = usuarioService.getByNombreUsuario((String) body.get("sub"));
        usuario.setEstaValidado(true);
        usuarioService.create(usuario);
        return "redirect:/auth/login?verify=true";
    }

    private void sendVerificationMail(String mail, String token) throws MessagingException {
        userConfirmationSender.sendMessage(mail, token);
    }



}
