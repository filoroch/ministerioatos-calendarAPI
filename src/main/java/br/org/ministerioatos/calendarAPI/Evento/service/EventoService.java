package br.org.ministerioatos.calendarAPI.Evento.service;

import br.org.ministerioatos.calendarAPI.Evento.DTOs.EventoRequestDTO;
import br.org.ministerioatos.calendarAPI.Evento.DTOs.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.Evento.model.Evento;
import br.org.ministerioatos.calendarAPI.Evento.repository.IEventoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService {

    @Autowired
    private IEventoRepository eventoRepository;

    // TODO: recuperar todos os eventos
    public List<EventoResponseDTO> findAllEvents(){
        return eventoRepository.findAll().stream().map(EventoService::toDTO).toList();
    }
    // TODO: recuperar evento por id
    public EventoResponseDTO findById(Integer idEvento){
        var event =  eventoRepository.findById(idEvento).orElseThrow(() -> new RuntimeException("Erro"));
        return toDTO(event);
    }
    // TODO: recuperar evento por data usando specification ou query
    // TODO: recuperar evento por titulo
    // TODO: criar evento
    public EventoResponseDTO createEvent(EventoRequestDTO evento){
        var descricao = evento.descricao().isEmpty() || evento.descricao() == null ? "" :  evento.descricao().get();
        var dataFim = evento.dataFim() == null || evento.dataFim() == null ? evento.dataInicio() : evento.dataFim().get();

        var newEvent = Evento.builder()
                .titulo(evento.titulo())
                .descricao(descricao)
                .dataInicio(evento.dataInicio())
                .dataFim(dataFim)
                .build();

        var saveEvent = eventoRepository.save(newEvent);
        return toDTO(saveEvent);
    }
    // TODO: atualizar evento
    @Transactional
    public boolean updateEvent(Integer idEvento, EventoRequestDTO evento){
        var eventTarget = eventoRepository.findById(idEvento).orElseThrow(() -> new RuntimeException("Erro"));
        var eventIsUpdated = false;

        if (evento.titulo() != null || evento.titulo().isEmpty()){
            eventTarget.setTitulo(evento.titulo());
            eventIsUpdated = true;
        }
        if (evento.descricao() != null || evento.descricao().isEmpty()){
            eventTarget.setDescricao(evento.descricao().get());
            eventIsUpdated = true;
        }
        if (evento.dataInicio() != null || evento.dataInicio().isEqual(null)){
            eventTarget.setDataInicio(evento.dataInicio());
            eventIsUpdated = true;
        }
        if (evento.dataFim() != null || evento.dataFim().isEmpty()){
            eventTarget.setDataFim(evento.dataFim().get());
            eventIsUpdated = true;
        }

        eventoRepository.save(eventTarget);
        return eventIsUpdated;
    }
    // TODO: excluir evento
    public boolean deleteEvent(Integer idEvent){
        var targetEventWithAnDelete = eventoRepository.findById(idEvent).orElseThrow(() -> new RuntimeException("Erro"));
        var eventIsDeleted = false;

        try {
            eventoRepository.delete(targetEventWithAnDelete);
            return eventIsDeleted = true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar o evento");
        }
    }

    static EventoResponseDTO toDTO(Evento evento){
        var descricao = evento.getDescricao().isEmpty() ? "" : evento.getDescricao();
        var dataFim = evento.getDataFim() == null ? evento.getDataInicio() : evento.getDataFim();

        return EventoResponseDTO.builder()
                .titulo(evento.getTitulo())
                .descricao(descricao)
                .datainicio(evento.getDataInicio())
                .dataFim(dataFim)
                .build();

    }
}
