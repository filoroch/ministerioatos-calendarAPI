package br.org.ministerioatos.calendarAPI.module.Evento.service;

import br.org.ministerioatos.calendarAPI.exceptions.BusinessError;
import br.org.ministerioatos.calendarAPI.exceptions.evento.EventAlredyExists;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request.EventoRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request.SubEventoRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.SubEventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.model.Evento;
import br.org.ministerioatos.calendarAPI.module.Evento.model.SubEvento;
import br.org.ministerioatos.calendarAPI.module.Evento.repository.EventoSpecification;
import br.org.ministerioatos.calendarAPI.module.Evento.repository.IEventoRepository;
import br.org.ministerioatos.calendarAPI.module.Local.model.Local;
import br.org.ministerioatos.calendarAPI.module.Local.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private IEventoRepository eventoRepository;

    @Autowired
    private LocalService localService;

    public Page<EventoResponseDTO> findAllEvents(Integer pageNumber, Integer pageSize, String sortBy, String sortDir){
        PageRequest page = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortDir), sortBy)
        );

        Page<Evento> eventsPage = eventoRepository.findAll(page);
        var results = eventsPage.map(EventoService::toDTO);

        return results;
    }

    public Page<EventoResponseDTO> findByFilters(
            String title,
            LocalDateTime start,
            LocalDateTime end,
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir
    ){
        var spec = EventoSpecification.containsTitle(title)
                .and(EventoSpecification.isDateInRange(start, end));

        var page = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.fromString(sortDir), sortBy)
        );

        var events = eventoRepository.findAll(spec, page);

        return events.map(EventoService::toDTO);
    }

    // TODO: recuperar evento por id
    public EventoResponseDTO findById(Integer idEvento){
        var event =  eventoRepository.findById(idEvento).orElseThrow(() -> new RuntimeException("Erro"));
        return toDTO(event);
    }

    public EventoResponseDTO createEvent(EventoRequestDTO evento){

        /// Determinando valores iniciais
        var dataHoraInicio = evento.dataHoraInicio().orElse(LocalDateTime.now());
        var descricao = evento.descricao().orElse("");
        var dataHoraFim = evento.dataFim().orElseGet(() -> dataHoraInicio.plusMinutes(30));

        eventIsExists(evento.titulo(), dataHoraInicio);

        var local = resolverLocal(evento);

        var newEvent = Evento.builder()
                .titulo(evento.titulo())
                .descricao(descricao)
                .dataHoraInicio(dataHoraInicio)
                .dataHoraFim(dataHoraFim)
                .local(local)
                .build();

        var subEventos = setSubEvents(evento.subEventos(), newEvent);
        newEvent.setSubEventos(subEventos);

        var saveEvent = eventoRepository.save(newEvent);
        return toDTO(saveEvent);
    }
    // TODO: atualizar evento
//    @Transactional
//    public boolean updateEvent(Integer idEvento, EventoRequestDTO evento){
//        var eventTarget = eventoRepository.findById(idEvento).orElseThrow(() -> new RuntimeException("Erro"));
//        var eventIsUpdated = false;
//
//        if (evento.titulo() != null || evento.titulo().isEmpty()){
//            eventTarget.setTitulo(evento.titulo());
//            eventIsUpdated = true;
//        }
//        if (evento.descricao() != null || evento.descricao().isEmpty()){
//            eventTarget.setDescricao(evento.descricao().get());
//            eventIsUpdated = true;
//        }
//        if (evento.dataInicio() != null || evento.dataInicio().isEqual(null)){
//            eventTarget.setDataInicio(evento.dataInicio());
//            eventIsUpdated = true;
//        }
//        if (evento.dataFim() != null || evento.dataFim().isEmpty()){
//            eventTarget.setDataFim(evento.dataFim().get());
//            eventIsUpdated = true;
//        }
//
//        eventoRepository.save(eventTarget);
//        return eventIsUpdated;
//    }
    // TODO: excluir evento
    public boolean deleteEvent(Integer idEvent){
        var targetEventWithAnDelete = eventoRepository.findById(idEvent).orElseThrow(() -> new RuntimeException("Erro"));
        var eventIsDeleted = false;

        try {
            eventoRepository.delete(targetEventWithAnDelete);
            return eventIsDeleted = true ;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar o evento");
        }
    }

    /// Metodos auxiliares

    private SubEvento createSubEvent(
            SubEventoRequestDTO dto,
            Evento parent
    ){
        var subEvento = new SubEvento();
        subEvento.setEvento(parent);
        subEvento.setTitulo(dto.titulo());
        subEvento.setDataInicio(dto.dataInicio());
        return subEvento;
    }

    private List<SubEvento> setSubEvents(
            List<SubEventoRequestDTO> dtos,
            Evento parent
    ){
        return dtos.stream()
                .map(dto -> createSubEvent(dto, parent))
                .collect(Collectors.toList());
    }

    private void eventIsExists (String titulo, LocalDateTime dataHoraInicio){
        var events = eventoRepository.findByTituloAndDataHoraInicio(titulo, dataHoraInicio);

        if (!events.isEmpty()){
            throw new EventAlredyExists();
        }
    }

    private Local resolverLocal(EventoRequestDTO evento){
        if (evento.idLocalExistente().isPresent()){
            return localService.findById(evento.idLocalExistente().get());
        } else if (evento.local().isPresent()){
            return localService.createLocalIfNotExists(evento.local().get());
        } else {
            throw new IllegalArgumentException("É necessário informar um local para o evento");
        }
    }

    static EventoResponseDTO toDTO(Evento evento){
        var subEventosDTO = evento.getSubEventos().stream()
                .map(sub -> new SubEventoResponseDTO(
                        sub.getTitulo(),
                        sub.getDataInicio()
                ))
                .toList();

        return EventoResponseDTO.builder()
                .titulo(evento.getTitulo())
                .descricao(evento.getDescricao())
                .dataHoraInicio(evento.getDataHoraInicio())
                .dataHoraFim(evento.getDataHoraFim())
                .subEventos(subEventosDTO)
                .local(evento.getLocal())
                .build();
    }


}
