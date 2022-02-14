package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.CalendarEventDTO;
import com.daw.ticketsdaw.DTOs.SalaDTO;
import com.daw.ticketsdaw.Entities.PropietarioSala;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Entities.Usuario;
import com.daw.ticketsdaw.Services.ButacaService;
import com.daw.ticketsdaw.Services.CiudadService;
import com.daw.ticketsdaw.Services.SalaService;
import com.daw.ticketsdaw.Services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;
    @Autowired
    private ButacaService butacaService;
    @Autowired
    private CiudadService ciudadService;
    @Autowired
    private UsuarioService usuarioService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping({"/",""})
    public String index(ModelMap modelMap, HttpSession session){
        PropietarioSala propietarioSala = getPropietario(session);
        modelMap.addAttribute("salas", propietarioSala.getSalas());
        return "salas/index";

    }

    @GetMapping("{id}/jsonSesiones")
    @ResponseBody
    public List<CalendarEventDTO> jsonSesiones(ModelMap modelMap, @PathVariable("id") int salaId, HttpSession session){
        Sala sala = salaService.read(salaId);
        if (checkPropietarioSala(sala,session)){
            List<CalendarEventDTO> sesiones = new ArrayList<>();

            for (var sesion : sala.getSesiones()) {
                sesiones.add(new CalendarEventDTO(sesion));
            }

            return sesiones;
        }
        return null;
    }

    @GetMapping("{id}")
    public String show(ModelMap modelMap, @PathVariable("id") int salaId, HttpSession session){
        Sala sala = salaService.read(salaId);
        if (checkPropietarioSala(sala,session)){
            modelMap.addAttribute("sala",sala);
            modelMap.addAttribute("butacas",salaService.getButacasJson(sala));
            return "salas/show";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable("id") int salaId, HttpSession session){
        Sala sala = salaService.read(salaId);

        if(!sala.isEstaOculto())
            return "redirect:/salas?error=edit_visible";

        if (checkPropietarioSala(sala,session)) {
            salaService.delete(sala);
            return "redirect:/salas";
        }
        return "redirect:/auth/login?error=unauthorized";
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
    @Transactional
    public String update(@PathVariable("id") Integer id, ModelMap modelMap, HttpSession session){
        Sala sala = salaService.read(id);

        if(!sala.isEstaOculto())
            return "redirect:/salas?error=edit_visible";

        if (checkPropietarioSala(sala,session) && sala.isEstaOculto()) {
            modelMap.addAttribute("sala", sala);
            modelMap.addAttribute("ciudades", ciudadService.read());
            return "salas/form";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @PostMapping({"/",""})
    @Transactional
    public String saveSala(ModelMap modelMap, @ModelAttribute @Valid SalaDTO salaDTO, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            modelMap.addAttribute("sala",salaDTO);
            modelMap.addAttribute("ciudades",ciudadService.read());
            modelMap.addAttribute("error", "Validation error");
            return "salas/form";
        }

        Sala sala = modelMapper.map(salaDTO, Sala.class);

        if(sala.getId()!=null){
            Sala salaPrevState = salaService.read(sala.getId());

            if(!salaPrevState.isEstaOculto())
                return "redirect:/salas?error=edit_visible";

            if(!checkPropietarioSala(salaPrevState,session)){
                return "redirect:/auth/login?error=unauthorized";
            }
        }

        PropietarioSala propietarioSala = getPropietario(session);
        sala.setPropietarioSala(propietarioSala);

        salaService.create(sala);
        return "redirect:/salas";
    }

    @GetMapping("{id}/butacas")
    public String showButacasForm(ModelMap modelMap, @PathVariable("id") int salaId, HttpSession session){
        Sala sala = salaService.read(salaId);

        if(!checkPropietarioSala(sala, session))
            return "redirect:/auth/login?error=unauthorized";

        modelMap.addAttribute("sala",sala);
        modelMap.addAttribute("butacas",salaService.getButacasJson(sala));
        return "salas/butacas-form";
    }

    @PostMapping("{id}/butacas")
    public String submitButacas(@RequestBody String butacas, @PathVariable int id, HttpSession session) throws JsonProcessingException {
        Sala sala = salaService.read(id);
        if (checkPropietarioSala(sala,session)){
            System.out.println(butacas);
            butacaService.deleteBySala(sala);
            salaService.setButacasJson(sala,butacas);
            return "redirect:/auth/register/propietario";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    private boolean checkPropietarioSala(Sala sala, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return sala.getPropietarioSala().getId() == usuario.getId();
    }

    private PropietarioSala getPropietario(HttpSession session){
        return (PropietarioSala) usuarioService.getById(
                ((Usuario) session.getAttribute("usuario")).getId()
        );
    }

}
