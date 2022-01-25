package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.EventoDTO;
import com.daw.ticketsdaw.Entities.Evento;
import com.daw.ticketsdaw.Entities.RecursoMedia;
import com.daw.ticketsdaw.Repositories.OrganizadorRepository;
import com.daw.ticketsdaw.Repositories.RecursoMediaRepository;
import com.daw.ticketsdaw.Services.EventosService;
import com.daw.ticketsdaw.Services.RecursoMediaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    EventosService eventosService;
    @Autowired
    RecursoMediaService mediaService;

    @Autowired
    Environment environment;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping({"/", ""})
    public String show(ModelMap modelMap){
        modelMap.addAttribute("eventos", eventosService.read());
        return "eventos/index";
    }

    @GetMapping({"/{id}"})
    public String showOne(ModelMap modelMap, @PathVariable(name="id") Integer eventoId){
        modelMap.addAttribute("evento", eventosService.read(eventoId));
        return "eventos/show";
    }

    @GetMapping({"create"})
    public String showForm(ModelMap model){
        model.addAttribute("evento", new Evento());
        return "eventos/create";
    }

    @GetMapping({"/{id}/update"})
    public String showUpdateForm(ModelMap model, @PathVariable(name="id") Integer eventoId){
        Evento evento = eventosService.read(eventoId);
        model.addAttribute("evento", evento);
        return "eventos/create";
    }

    @PostMapping({"/", ""})
    @Transactional(rollbackFor = {IOException.class})
    public String saveEvento(@ModelAttribute @Valid EventoDTO eventoDTO, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return "redirect:/eventos/create?error=validation";
        }

        //If ID is null, this is a create operation, and all NonNull file inputs are mandatory
        if(eventoDTO.getId() == null){
            if(eventoDTO.getDocumentoNormas().isEmpty())
                return "redirect:/eventos/create?error=validation";


        }

        Evento evento = modelMapper.map(eventoDTO, Evento.class);

        //File upload code
        Path path = Paths.get(environment.getProperty("tickets.uploads.path") + "randomText");
        //multipartFile.transferTo(path);

        /*evento.setFotoPerfil(repo.getById(1));
        evento.setOrganizador(repo2.getById(2));*/

        eventosService.save(evento);
        return "redirect:/eventos";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteEvento(ModelMap map, @PathVariable(name="id") Integer eventoId){
        Evento evento = new Evento();
        evento.setId(eventoId);
        eventosService.remove(evento);
        return "redirect:/eventos";
    }
}
