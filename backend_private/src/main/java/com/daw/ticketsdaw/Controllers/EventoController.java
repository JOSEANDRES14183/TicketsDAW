package com.daw.ticketsdaw.Controllers;

import com.daw.ticketsdaw.DTOs.*;

import com.daw.ticketsdaw.Entities.*;
import com.daw.ticketsdaw.Services.*;
import org.apache.commons.lang3.time.DateUtils;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        Organizador organizador = getOrganizador(session);
        modelMap.addAttribute("eventos", organizador.getEventos());
        return "eventos/index";
    }

    @GetMapping({"/{id}"})
    public String showOne(ModelMap modelMap, @PathVariable(name="id") Integer eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento, session)){
            modelMap.addAttribute("evento", evento);
            return "eventos/show";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @GetMapping({"/{id}/jsonSesiones"})
    @ResponseBody
    public List<CalendarEventDTO> jsonSesiones(@PathVariable(name="id") Integer eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento, session)){
            List<CalendarEventDTO> sesiones = new ArrayList<>();

            for (var sesion : evento.getSesiones()) {
                sesiones.add(new CalendarEventDTO(sesion));
            }

            return sesiones;
        }
        return null;
    }

    @GetMapping({"create"})
    public String showForm(ModelMap model){
        model.addAttribute("evento", new Evento());
        model.addAttribute("categorias", categoriaService.read());
        return "eventos/create";
    }

    @GetMapping({"/{id}/update"})
    public String showUpdateForm(ModelMap model, @PathVariable(name="id") Integer eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);

        if(!evento.isEstaOculto())
            return "redirect:/eventos?error=edit_visible";

        if (checkOrganizador(evento, session)) {
            model.addAttribute("evento", evento);
            model.addAttribute("categorias", categoriaService.read());
            return "eventos/create";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @PostMapping({"/", ""})
    @Transactional(rollbackFor = {IOException.class})
    public String saveEvento(ModelMap model, @ModelAttribute @Valid EventoDTO eventoDTO, BindingResult bindingResult, HttpSession session) throws IOException {
        Evento eventoPrevState;
        Evento evento = modelMapper.map(eventoDTO, Evento.class);

        boolean fileInputCheckPassed = true;

        //If this is an update operation, check if the user is allowed to update this event
        if(evento.getId() != null){
            eventoPrevState = eventosService.read(eventoDTO.getId());

            if(!eventoPrevState.isEstaOculto())
                return "redirect:/eventos?error=edit_visible";

            if(!checkOrganizador(eventoPrevState, session)){
                return "redirect:/auth/login?error=unauthorized";
            }
        }
        //If this is a create operation, check all NonNull file inputs
        else{
            if(eventoDTO.getFotoPerfil().isEmpty())
                fileInputCheckPassed = false;
            eventoPrevState = new Evento();
        }

        if(bindingResult.hasErrors() || !fileInputCheckPassed){
            model.addAttribute("evento", evento);
            model.addAttribute("categorias", categoriaService.read());
            model.addAttribute("error", "Validation error");
            return "eventos/create";
        }

        //Check mandatory fields if Evento is visible
        if(!eventoDTO.isEstaOculto()){
            if(!(eventoDTO.getId() != null && sesionService.countPublicByEvento(eventosService.read(eventoDTO.getId())) > 0)){
                model.addAttribute("evento", evento);
                model.addAttribute("categorias", categoriaService.read());
                model.addAttribute("error", "Validation error: You can't set an event to public without having public sessions in it");
                return "eventos/create";
            }
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

        Organizador organizador = getOrganizador(session);
        evento.setOrganizador(organizador);

        eventosService.save(evento);
        return "redirect:/eventos";
    }

    @GetMapping({"/{id}/delete"})
    public String deleteEvento(@PathVariable(name="id") Integer eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);

        if(!evento.isEstaOculto())
            return "redirect:/eventos?error=edit_visible";

        if (checkOrganizador(evento, session)) {
            eventosService.remove(evento);
            return "redirect:/eventos";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @GetMapping({"/{id}/normas_evento/delete"})
    @Transactional
    public String deleteNormasEvento(@PathVariable(name="id") Integer eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);

        if(!evento.isEstaOculto())
            return "redirect:/eventos?error=edit_visible";

        if (checkOrganizador(evento, session)) {
            var documentoNormas = evento.getDocumentoNormas();
            evento.setDocumentoNormas(null);
            eventosService.save(evento);
            normasService.deleteNormas(documentoNormas);
            return "redirect:/eventos/" + eventoId;
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @GetMapping({"/{id}/images/add"})
    public String showImgForm(ModelMap model, @PathVariable(name="id") Integer eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento, session)) {
            model.addAttribute("evento", evento);
            model.addAttribute("galeria", new GaleriaDTO());
            return "eventos/galerias/create";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @PostMapping({"/{id}/images/add"})
    @Transactional(rollbackFor = {IOException.class})
    public String addImage(@ModelAttribute @Valid GaleriaDTO galeriaDTO, @PathVariable(name="id") Integer eventoId, BindingResult bindingResult, HttpSession session) throws IOException {
        if(bindingResult.hasErrors()){
            return "redirect:/eventos/" + eventoId + "/images/add?error=validation";
        }

        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento, session)) {
            RecursoMedia formSubmittedMedia = modelMapper.map(galeriaDTO, RecursoMedia.class);
            mediaService.saveImageGallery(galeriaDTO.getMedia(), formSubmittedMedia, evento);
            return "redirect:/eventos/" + eventoId;
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @PostMapping({"/{eventoId}/images/{mediaId}/update"})
    @Transactional(rollbackFor = {IOException.class})
    public String changePriority(@PathVariable Integer eventoId, @PathVariable Integer mediaId, @RequestParam Integer prioridad, HttpSession session) throws IOException {
        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento, session)) {
            RecursoMedia media = mediaService.read(mediaId);

            if (!media.getEventoGaleriaImagenes().equals(evento))
                return "redirect:/eventos/" + eventoId + "?error=unauthorized";

            media.setPrioridad(prioridad);

            mediaService.save(media);

            return "redirect:/eventos/" + eventoId;
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @GetMapping({"/{eventoId}/images/{mediaId}/delete"})
    public String deleteGallery(@PathVariable Integer eventoId, @PathVariable Integer mediaId, HttpSession session){
        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento, session)) {
            RecursoMedia media = mediaService.read(mediaId);

            if (!media.getEventoGaleriaImagenes().equals(evento))
                return "redirect:/eventos/" + eventoId + "?error=unauthorized";

            mediaService.delete(media);

            return "redirect:/eventos/" + eventoId;
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @GetMapping({"/{eventoId}/sesiones_no_num/create"})
    public String showFormNoNum(ModelMap model, @PathVariable Integer eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);

        if(!checkOrganizador(evento, session))
            return "redirect:/auth/login?error=unauthorized";

        model.addAttribute("sesion", new SesionNoNumerada());
        model.addAttribute("salas", salaService.readVisible());
        model.addAttribute("evento", evento);
        return "eventos/sesiones/session-no-numerada-form";
    }

    @GetMapping("/{eventoId}/sesiones/{sesionId}/delete")
    public String deleteSesion(@PathVariable int eventoId, @PathVariable int sesionId, HttpSession session){
        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento,session)){
            Sesion sesion = sesionService.read(sesionId);

            if(!sesion.isEstaOculto())
                return "redirect:/eventos/" + evento.getId() + "?error=edit_visible";

            if (sesion.getEvento().equals(evento)){
                sesionService.delete(sesion);
                return "redirect:/eventos/"+evento.getId();
            }
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @GetMapping({"/{eventoId}/sesiones_no_num/{sesionId}/update"})
    public String updateNoNum(ModelMap model, @PathVariable Integer eventoId, @PathVariable Integer sesionId, HttpSession session){
        Evento evento = eventosService.read(eventoId);

        if(!checkOrganizador(evento, session))
            return "redirect:/auth/login?error=unauthorized";

        SesionNoNumerada sesion = (SesionNoNumerada) sesionService.read(sesionId);

        if(!sesion.isEstaOculto())
            return "redirect:/eventos/" + evento.getId() + "?error=edit_visible";

        if (!sesion.getEvento().equals(evento))
            return "redirect:/auth/login?error=unauthorized";

        model.addAttribute("sesion", sesion);
        model.addAttribute("salas", salaService.readVisible());
        model.addAttribute("evento", evento);
        return "eventos/sesiones/session-no-numerada-form";
    }

    @GetMapping({"/{eventoId}/sesiones/{sesionId}/copy"})
    public String copySesionForm(ModelMap model, @PathVariable Integer eventoId, @PathVariable Integer sesionId, HttpSession session){
        Evento evento = eventosService.read(eventoId);

        if(!checkOrganizador(evento, session))
            return "redirect:/auth/login?error=unauthorized";

        Sesion sesion = sesionService.read(sesionId);

        if (!sesion.getEvento().equals(evento))
            return "redirect:/auth/login?error=unauthorized";

        model.addAttribute("copyDTO", new CopySesionDTO());
        model.addAttribute("returnURL", "/eventos/" + eventoId + "/sesiones_no_num/" + sesionId + "/copy");
        return "eventos/sesiones/copy/form";
    }

    @PostMapping({"/{eventoId}/sesiones/{sesionId}/copy"})
    @Transactional(rollbackFor = {IOException.class})
    public String copySesion(@Valid @ModelAttribute CopySesionDTO copySesionDTO, BindingResult bindingResult, @PathVariable Integer eventoId, @PathVariable int sesionId, HttpSession session) throws IOException {
        Evento evento = eventosService.read(eventoId);

        if(!checkOrganizador(evento, session))
            return "redirect:/auth/login?error=unauthorized";

        Sesion sesion = sesionService.read(sesionId);

        if (!sesion.getEvento().equals(evento))
            return "redirect:/auth/login?error=unauthorized";

        if(bindingResult.hasErrors())
            return "redirect:/eventos/" + eventoId + "?error=validation";

        List<SesionDTO> sesiones = new ArrayList<>();

        int daysBetween = copySesionDTO.getNumDays();

        for (Date d = DateUtils.addDays(sesion.getFechaIni(), daysBetween); d.before(copySesionDTO.getEndDate()); d = DateUtils.addDays(d, daysBetween)) {
            sesiones.add(generateSesionDTO(sesion, d));
        }

        //Generate SesionNoNumerada from DTO

        boolean savedWithoutOverlap = saveSesionCopies(evento, sesiones);

        return "redirect:/eventos/" + eventoId + (savedWithoutOverlap ? "" : "?warning=overlap");
    }

    private SesionDTO generateSesionDTO(Sesion sesion, Date dateNew){
        SesionDTO sesionDTO = null;
        if(sesion instanceof SesionNoNumerada)
            sesionDTO = modelMapper.map(sesion, SesionNoNumeradaDTO.class);
        if(sesion instanceof SesionNumerada)
            sesionDTO = modelMapper.map(sesion, SesionNumeradaDTO.class);

        sesionDTO.setId(null);
        sesionDTO.setEstaOculto(true);

        int finVentaDiffSeconds = (int) TimeUnit.SECONDS.convert( sesionDTO.getFechaFinVenta().getTime() - sesionDTO.getFechaIni().getTime(), TimeUnit.MILLISECONDS);
        sesionDTO.setFechaIni(dateNew);
        sesionDTO.setFechaFinVenta(DateUtils.addSeconds(dateNew, finVentaDiffSeconds));


        if(sesionDTO instanceof SesionNoNumeradaDTO) {
            SesionNoNumeradaDTO sesionNoNumeradaDTO = (SesionNoNumeradaDTO) sesionDTO;
            SesionNoNumerada sesionNoNumerada = (SesionNoNumerada) sesion;

            List<String> nombreTipo = new ArrayList<>();
            List<Integer> maxEntradasTipo = new ArrayList<>();
            List<Float> precioTipo = new ArrayList<>();
            for (var tipo:
                    sesionNoNumerada.getTiposEntrada()) {
                nombreTipo.add(tipo.getPrimaryKey().getNombre());
                maxEntradasTipo.add(tipo.getMaxEntradas());
                precioTipo.add(tipo.getPrecio());
            }
            sesionNoNumeradaDTO.setNombreTipo(nombreTipo);
            sesionNoNumeradaDTO.setMaxEntradasTipo(maxEntradasTipo);
            sesionNoNumeradaDTO.setPrecioTipo(precioTipo);
        }

        return sesionDTO;
    }

    private boolean saveSesionCopies(Evento evento, List<SesionDTO> sesionDTOs){
        boolean savedWithoutOverlap = true;
        for(var sesionDTO : sesionDTOs){
            Sesion sesionCopy = null;
            if(sesionDTO instanceof SesionNoNumeradaDTO)
                sesionCopy = modelMapper.map(sesionDTO, SesionNoNumerada.class);
            if(sesionDTO instanceof SesionNumeradaDTO)
                sesionCopy = modelMapper.map(sesionDTO, SesionNumerada.class);

            sesionCopy.setEvento(evento);

            if(!sesionService.save(sesionCopy))
                savedWithoutOverlap = false;

            if(sesionCopy instanceof SesionNoNumerada){
                List<TipoEntrada> tipoEntradaList = generateTiposEntrada((SesionNoNumerada) sesionCopy, (SesionNoNumeradaDTO) sesionDTO);

                for (var tipo :
                        tipoEntradaList) {
                    tipoEntradaService.save(tipo);
                }
            }
        }
        return savedWithoutOverlap;
    }

    @PostMapping({"/{eventoId}/sesiones_no_num"})
    @Transactional(rollbackFor = {Exception.class})
    public String saveSesionNoNum(ModelMap model, @Valid @ModelAttribute SesionNoNumeradaDTO sesionDTO, BindingResult bindingResult, @PathVariable Integer eventoId, HttpSession session) throws IOException {
        Evento evento = eventosService.read(eventoId);

        if(!checkOrganizador(evento, session))
            return "redirect:/auth/login?error=unauthorized";

        //Check if TipoEntrada inputs have a valid structure
        if(!(sesionDTO.getMaxEntradasTipo().size() == sesionDTO.getNombreTipo().size() &&
                sesionDTO.getNombreTipo().size() == sesionDTO.getPrecioTipo().size())){
            return "redirect:/eventos/" + eventoId + "/sesiones_no_num/create?error=validation";
        }

        SesionNoNumerada sesion = modelMapper.map(sesionDTO, SesionNoNumerada.class);

        if(bindingResult.hasErrors() || sesionDTO.getSala().isEstaOculto()){
            model.addAttribute("evento", evento);
            model.addAttribute("salas", salaService.readVisible());

            //Parse current TiposEntrada to return to the form
            List<TipoEntrada> tipoEntradaList = generateTiposEntrada(sesion, sesionDTO);
            sesion.setTiposEntrada(tipoEntradaList);

            model.addAttribute("sesion", sesion);
            model.addAttribute("error", "Validation error");
            return "eventos/sesiones/session-no-numerada-form";
        }

        //Check mandatory fields if the Sesion is visible
        if(!sesionDTO.isEstaOculto()){
            if(sesionDTO.getMaxEntradasTipo().size() <= 0){
                model.addAttribute("evento", evento);
                model.addAttribute("salas", salaService.readVisible());

                //Parse current TiposEntrada to return to the form
                List<TipoEntrada> tipoEntradaList = generateTiposEntrada(sesion, sesionDTO);
                sesion.setTiposEntrada(tipoEntradaList);

                model.addAttribute("sesion", sesion);
                model.addAttribute("error", "Validation error: You must declare at least 1 ticket type");
                return "eventos/sesiones/session-no-numerada-form";
            }
        }

        if(sesionDTO.getId() != null){
            //If Sesion had TiposEntrada previously, put them back before saving the object, if not a bug occurs and the TiposEntrada list appears empty even though they aren't dropped from the database
            SesionNoNumerada sesionPrevState = (SesionNoNumerada) sesionService.read(sesionDTO.getId());
            sesion.setTiposEntrada(sesionPrevState.getTiposEntrada());

            if(!sesionPrevState.isEstaOculto())
                return "redirect:/eventos/" + evento.getId() + "?error=edit_visible";

            //Check if evento id has changed since last submission
            if(sesionPrevState.getEvento().getId() != eventoId)
                return "redirect:/eventos?error=validation";

            if(!sesionPrevState.getEvento().equals(evento))
                return "redirect:/auth/login?error=unauthorized";
        }

        sesion.setEvento(evento);

        boolean savedWithoutOverlap = sesionService.save(sesion);

        if(sesion.getTiposEntrada() != null){
            for (var tipoEntrada: sesion.getTiposEntrada()) {
                tipoEntradaService.delete(tipoEntrada);
            }
        }

        List<TipoEntrada> tipoEntradaList = generateTiposEntrada(sesion, sesionDTO);

        for (var tipo :
                tipoEntradaList) {
            tipoEntradaService.save(tipo);
        }

        return "redirect:/eventos/" + eventoId + (savedWithoutOverlap ? "" : "?warning=overlap");
    }

    private List<TipoEntrada> generateTiposEntrada(SesionNoNumerada sesion, SesionNoNumeradaDTO sesionDTO){
        List<TipoEntrada> tipoEntradaList = new ArrayList<>();
        for (int i = 0; i < sesionDTO.getMaxEntradasTipo().size(); i++){
            var tipo = new TipoEntrada();
            tipo.setPrimaryKey(new TipoEntradaId(sesion.getId(), sesionDTO.getNombreTipo().get(i)));
            tipo.setEntitySesion(sesion);
            tipo.setMaxEntradas(sesionDTO.getMaxEntradasTipo().get(i));
            tipo.setPrecio(sesionDTO.getPrecioTipo().get(i));
            tipoEntradaList.add(tipo);
        }
        return tipoEntradaList;
    }

    @GetMapping("/{eventoId}/sesiones_num/create")
    public String showCreateSesionNumerada(ModelMap modelMap, @PathVariable int eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento, session)) {
            modelMap.addAttribute("sesion", new SesionNumerada());
            modelMap.addAttribute("salas", salaService.getSalasWithButacas());
            modelMap.addAttribute("evento", evento);
            return "eventos/sesiones/session-num-form";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @GetMapping("/{eventoId}/sesiones_num/{sesionId}/update")
    public String showUpdateSesionNumerada(ModelMap modelMap, @PathVariable int sesionId, @PathVariable int eventoId, HttpSession session){
        Evento evento = eventosService.read(eventoId);
        if (checkOrganizador(evento, session)) {

            SesionNumerada sesion = (SesionNumerada) sesionService.read(sesionId);

            if(!sesion.isEstaOculto())
                return "redirect:/eventos/" + evento.getId() + "?error=edit_visible";

            if (!sesion.getEvento().equals(evento))
                return "redirect:/auth/login?error=unauthorized";

            modelMap.addAttribute("sesion", sesion);
            modelMap.addAttribute("salas", salaService.getSalasWithButacas());
            modelMap.addAttribute("evento", evento);
            return "eventos/sesiones/session-num-form";
        }
        return "redirect:/auth/login?error=unauthorized";
    }

    @PostMapping("/{eventoId}/sesiones_num")
    public String saveSesionNum(ModelMap modelMap, @Valid @ModelAttribute SesionNumeradaDTO sesionNumeradaDTO, BindingResult bindingResult , @PathVariable int eventoId, HttpSession session) {
        Evento evento = eventosService.read(eventoId);

        if(!checkOrganizador(evento, session))
            return "redirect:/auth/login?error=unauthorized";

        if (bindingResult.hasErrors() || sesionNumeradaDTO.getSala().isEstaOculto() || !sesionNumeradaDTO.getSala().hasButacas()){
            modelMap.addAttribute("sesion", sesionNumeradaDTO);
            modelMap.addAttribute("salas", salaService.getSalasWithButacas());
            modelMap.addAttribute("evento", evento);
            modelMap.addAttribute("error", "Validation error");
            return "eventos/sesiones/session-num-form";
        }
        SesionNumerada sesionNumerada = modelMapper.map(sesionNumeradaDTO, SesionNumerada.class);
        if (sesionNumerada.getId()!=null){
           Sesion sesionPrevState = sesionService.read(sesionNumerada.getId());

            if(!sesionPrevState.isEstaOculto())
                return "redirect:/eventos/" + evento.getId() + "?error=edit_visible";

            //Check if evento id has changed since last submission
            if(sesionPrevState.getEvento().getId() != eventoId)
                return "redirect:/eventos?error=validation";

            if(!sesionPrevState.getEvento().equals(evento))
                return "redirect:/auth/login?error=unauthorized";
        }

        sesionNumerada.setEvento(evento);

        boolean savedWithoutOverlap = sesionService.save(sesionNumerada);
        return "redirect:/eventos/" + eventoId + (savedWithoutOverlap ? "" : "?warning=overlap");
    }

    private boolean checkOrganizador(Evento evento, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return evento.getOrganizador().getId() == usuario.getId();
    }

    private Organizador getOrganizador(HttpSession session){
        return (Organizador) usuarioService.getById(
                ((Usuario) session.getAttribute("usuario")).getId()
        );
    }

}
