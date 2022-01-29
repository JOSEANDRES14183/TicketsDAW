package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.EventoDTO;
import com.daw.ticketsdaw.DTOs.GaleriaDTO;
import com.daw.ticketsdaw.Entities.Evento;
import com.daw.ticketsdaw.Entities.NormasEvento;
import com.daw.ticketsdaw.Entities.Organizador;
import com.daw.ticketsdaw.Entities.RecursoMedia;
import com.daw.ticketsdaw.Repositories.OrganizadorRepository;
import com.daw.ticketsdaw.Repositories.RecursoMediaRepository;
import com.daw.ticketsdaw.Services.CategoriaService;
import com.daw.ticketsdaw.Services.EventosService;
import com.daw.ticketsdaw.Services.NormasEventoService;
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
    NormasEventoService normasService;
    @Autowired
    CategoriaService categoriaService;

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
        model.addAttribute("categorias", categoriaService.read());
        return "eventos/create";
    }

    @GetMapping({"/{id}/update"})
    public String showUpdateForm(ModelMap model, @PathVariable(name="id") Integer eventoId){
        Evento evento = eventosService.read(eventoId);
        model.addAttribute("evento", evento);
        model.addAttribute("categorias", categoriaService.read());
        return "eventos/create";
    }

    @PostMapping({"/", ""})
    @Transactional(rollbackFor = {IOException.class})
    public String saveEvento(@ModelAttribute @Valid EventoDTO eventoDTO, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return "redirect:/eventos/create?error=validation";
        }

        Evento eventoPrevState;
        Evento evento = modelMapper.map(eventoDTO, Evento.class);

        //If ID is null, this is a create operation, and all NonNull file inputs are mandatory
        if(eventoDTO.getId() == null){
            if(eventoDTO.getFotoPerfil().isEmpty())
                return "redirect:/eventos/create?error=validation";
            eventoPrevState = new Evento();
        }
        else{
            eventoPrevState = eventosService.read(eventoDTO.getId());
        }


        RecursoMedia fotoPerfil = null;
        NormasEvento documentoNormas = null;

        if(eventoDTO.getFotoPerfil().isEmpty()){
            evento.setFotoPerfil(eventoPrevState.getFotoPerfil());
        }
        else{
            fotoPerfil = mediaService.createFromFile(eventoDTO.getFotoPerfil());
            evento.setFotoPerfil(fotoPerfil);
        }

        if(eventoDTO.getDocumentoNormas().isEmpty()){
            evento.setDocumentoNormas(eventoPrevState.getDocumentoNormas());
        }
        else{
            documentoNormas = normasService.createFromFile(eventoDTO.getDocumentoNormas());
            evento.setDocumentoNormas(documentoNormas);
        }

        //TODO: Get user id from context
        Organizador organizador = new Organizador();
        organizador.setId(2);

        evento.setOrganizador(organizador);

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

    @GetMapping({"/{id}/images/add"})
    public String showImgForm(ModelMap model, @PathVariable(name="id") Integer eventoId){
        model.addAttribute("evento", eventosService.read(eventoId));
        model.addAttribute("galeria", new GaleriaDTO());
        return "eventos/galerias/create";
    }

    @PostMapping({"/{id}/images/add"})
    @Transactional(rollbackFor = {IOException.class})
    public String addImage(@ModelAttribute @Valid GaleriaDTO galeriaDTO, @PathVariable(name="id") Integer eventoId, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return "redirect:/eventos/" + eventoId + "/images/add?error=validation";
        }

        RecursoMedia formSubmittedMedia = modelMapper.map(galeriaDTO, RecursoMedia.class);

        RecursoMedia savedMedia = mediaService.saveImageGallery(galeriaDTO.getMedia(), formSubmittedMedia, eventosService.read(eventoId));

        return "redirect:/eventos/" + eventoId;
    }
//"/{eventoId}/videos/{mediaId}/update"
    @PostMapping({"/{eventoId}/images/{mediaId}/update"})
    @Transactional(rollbackFor = {IOException.class})
    public String changePriority(@PathVariable Integer eventoId, @PathVariable Integer mediaId, @RequestParam Integer prioridad) throws IOException {
        Evento evento = eventosService.read(eventoId);
        RecursoMedia media = mediaService.read(mediaId);

        if(!media.getEventoGaleriaImagenes().equals(evento))
            return "redirect:/eventos/" + eventoId + "?error=unauthorized";

        media.setPrioridad(prioridad);

        mediaService.save(media);

        return "redirect:/eventos/" + eventoId;
    }

    @GetMapping({"/{eventoId}/images/{mediaId}/delete"})
    public String deleteGallery(@PathVariable Integer eventoId, @PathVariable Integer mediaId){
        Evento evento = eventosService.read(eventoId);
        RecursoMedia media = mediaService.read(mediaId);

        if(!media.getEventoGaleriaImagenes().equals(evento))
            return "redirect:/eventos/" + eventoId + "?error=unauthorized";

        mediaService.delete(media);

        return "redirect:/eventos/" + eventoId;
    }
}
