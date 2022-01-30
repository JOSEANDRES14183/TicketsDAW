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
import org.springframework.transaction.annotation.Transactional;
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
        if(session.getAttribute("usuario")!=null && session.getAttribute("usuario").getClass()== PropietarioSala.class){
            PropietarioSala propietarioSala = (PropietarioSala) usuarioService.getById(((Usuario) session.getAttribute("usuario")).getId());
            modelMap.addAttribute("salas", propietarioSala.getSalas());
            return "salas/index";
        }
        return "redirect:/login";
    }

    @GetMapping("{id}")
    public String show(ModelMap modelMap, @PathVariable("id") int salaId){
        Sala sala = salaService.read(salaId);
        modelMap.addAttribute("sala",sala);
        modelMap.addAttribute("butacas",salaService.getButacasJson(sala));
        return "salas/show";
    }

    @GetMapping("{id}/delete")
    public String delete(@PathVariable("id") int salaId){
        salaService.delete(salaService.read(salaId));
        return "redirect:/salas";
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
        public String update(@PathVariable("id") Integer id, ModelMap modelMap){
        modelMap.addAttribute("sala",salaService.read(id));
        modelMap.addAttribute("ciudades",ciudadService.read());
        return "salas/form";
    }

    @PostMapping({"/",""})
    public String saveSala(@ModelAttribute @Valid SalaDTO salaDTO, BindingResult bindingResult, HttpSession session){
        if(bindingResult.hasErrors()){
            return "redirect:/salas/create";
        }

        Sala sala = modelMapper.map(salaDTO, Sala.class);
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

}
