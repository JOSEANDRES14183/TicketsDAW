package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.SalaDTO;
import com.daw.ticketsdaw.Entities.PropietarioSala;
import com.daw.ticketsdaw.Entities.Sala;
import com.daw.ticketsdaw.Entities.Usuario;
import com.daw.ticketsdaw.Services.CiudadService;
import com.daw.ticketsdaw.Services.SalaService;
import com.daw.ticketsdaw.Services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;
    @Autowired
    private CiudadService ciudadService;
    @Autowired
    private UsuarioService usuarioService;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping({"/",""})
    public String index(ModelMap modelMap, HttpSession session){
        PropietarioSala propietarioSala = (PropietarioSala) usuarioService.getById(((Usuario) session.getAttribute("usuario")).getId());
        modelMap.addAttribute("salas", propietarioSala.getSalas());
        return "salas/index";

    }

    @GetMapping("{id}")
    public String show(ModelMap modelMap, @PathVariable("id") int salaId, HttpSession session){
        Sala sala = salaService.read(salaId);
        if (checkPropietarioSala(sala,session)){
            modelMap.addAttribute("sala",sala);
            modelMap.addAttribute("butacas",salaService.getButacasJson(sala));
            return "salas/show";
        }
        return "redirect:/auth/login";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable("id") int salaId, HttpSession session){
        Sala sala = salaService.read(salaId);
        if (checkPropietarioSala(sala,session)) {
            salaService.delete(sala);
            return "redirect:/salas";
        }
        return "redirect:/auth/login";
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
        public String update(@PathVariable("id") Integer id, ModelMap modelMap, HttpSession session){
        Sala sala = salaService.read(id);
        if (checkPropietarioSala(sala,session)) {
            modelMap.addAttribute("sala", sala);
            modelMap.addAttribute("ciudades", ciudadService.read());
            return "salas/form";
        }
        return "redirect:/auth/login";
    }

    @PostMapping({"/",""})
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
            if(!checkPropietarioSala(salaPrevState,session)){
                return "redirect:/auth/login";
            }
        }

        PropietarioSala propietarioSala = (PropietarioSala) usuarioService.getById(((Usuario) session.getAttribute("usuario")).getId());
        sala.setPropietarioSala(propietarioSala);

        salaService.create(sala);
        return "redirect:/salas";
    }

    @GetMapping("{id}/butacas")
    public String showButacasForm(ModelMap modelMap, @PathVariable("id") int salaId){
        Sala sala = salaService.read(salaId);
        modelMap.addAttribute("sala",sala);
        modelMap.addAttribute("butacas",salaService.getButacasJson(sala));
        return "salas/butacas-form";
    }

    private boolean checkPropietarioSala(Sala sala, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return sala.getPropietarioSala().getId() == usuario.getId();
    }

}
