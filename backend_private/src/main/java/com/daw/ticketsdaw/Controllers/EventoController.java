package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.EventoDTO;

import com.daw.ticketsdaw.DTOs.SesionNoNumeradaDTO;
import com.daw.ticketsdaw.Entities.*;
import com.daw.ticketsdaw.Services.*;
import com.daw.ticketsdaw.DTOs.GaleriaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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
    private UsuarioService usuarioService;
    @Autowired
    private SalaService salaService;
    @Autowired
    private SesionService sesionService;
    @Autowired
    private TipoEntradaService tipoEntradaService;

    @Autowired
    Environment environment;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping({"/", ""})
    public String show(ModelMap modelMap, HttpSession session){
        if(session.getAttribute("usuario")!=null && session.getAttribute("usuario").getClass()== Organizador.class){
            Organizador organizador = (Organizador) usuarioService.getById(((Usuario)session.getAttribute("usuario")).getId());
            modelMap.addAttribute("eventos", organizador.getEventos());
            return "eventos/index";
        }
        return "redirect:/login";
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
    public String saveEvento(@ModelAttribute @Valid EventoDTO eventoDTO, BindingResult bindingResult, HttpSession session) throws IOException {
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
        Organizador organizador = (Organizador) usuarioService.getById(((Usuario) session.getAttribute("usuario")).getId());
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

    @GetMapping({"/{eventoId}/sesiones_no_num/create"})
    public String showFormNoNum(ModelMap model, @PathVariable Integer eventoId){
        model.addAttribute("sesion", new SesionNoNumerada());
        model.addAttribute("salas", salaService.read());
        model.addAttribute("evento", eventosService.read(eventoId));
        return "eventos/sesiones/create-no-numerada";
    }

    @GetMapping({"/{eventoId}/sesiones_no_num/{sesionId}/update"})
    public String updateNoNum(ModelMap model, @PathVariable Integer eventoId, @PathVariable Integer sesionId){
        model.addAttribute("sesion", (SesionNoNumerada) sesionService.read(sesionId));
        model.addAttribute("salas", salaService.read());
        model.addAttribute("evento", eventosService.read(eventoId));
        return "eventos/sesiones/create-no-numerada";
    }

    @PostMapping({"/{eventoId}/sesiones_no_num"})
    @Transactional
    public String saveSesionNoNum(@Valid @ModelAttribute SesionNoNumeradaDTO sesionDTO, BindingResult bindingResult, @PathVariable Integer eventoId) throws IOException {
        if(bindingResult.hasErrors()){
            return "redirect:/eventos/" + eventoId + "/sesiones_no_num/create?error=validation";
        }

        SesionNoNumerada sesion = modelMapper.map(sesionDTO, SesionNoNumerada.class);

        if(!(sesionDTO.getMaxEntradasTipo().size() == sesionDTO.getNombreTipo().size() &&
                sesionDTO.getNombreTipo().size() == sesionDTO.getPrecioTipo().size())){
            return "redirect:/eventos/" + eventoId + "/sesiones_no_num/create?error=validation";
        }

        sesionService.save(sesion);

        for (int i = 0; i < sesionDTO.getMaxEntradasTipo().size(); i++){
            tipoEntradaService.save(new TipoEntrada(sesion, sesionDTO.getNombreTipo().get(i), sesionDTO.getMaxEntradasTipo().get(i), sesionDTO.getPrecioTipo().get(i)));
        }

        return "redirect:/eventos/" + eventoId;
    }

    @GetMapping("/{eventoId}/sesiones_num/create")
    public String showCreateSesionNumerada(ModelMap modelMap, @PathVariable int eventoId){
        modelMap.addAttribute("sesion",new SesionNumerada());
        modelMap.addAttribute("salasConButacas",salaService.getSalasWithButacas());
        return "eventos/sesiones/session-num-form";
    }

    @GetMapping("/{eventoId}/sesiones_num/{sesionId}/update")
    public String showUpdateSesionNumerada(ModelMap modelMap, @PathVariable int sesionId){
        modelMap.addAttribute("sesion",sesionService.read(sesionId));
        modelMap.addAttribute("salasConButacas",salaService.getSalasWithButacas());
        return "eventos/sesiones/session-num-form";
    }

    @PostMapping("/{eventoId}/sesiones_num")
    public String saveSesionNum(@Valid @ModelAttribute SesionNumerada sesionNumerada, @PathVariable int eventoId) {
        sesionNumerada.setEvento(eventosService.read(eventoId));
        sesionService.save(sesionNumerada);
        return "redirect:/eventos/"+sesionNumerada.getEvento().getId();
    }

}
