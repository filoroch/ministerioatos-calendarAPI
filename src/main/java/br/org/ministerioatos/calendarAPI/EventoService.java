package br.org.ministerioatos.calendarAPI;

import br.org.ministerioatos.calendarAPI.domain.exceptions.evento.EventAlredyExists;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.EventoSpecification;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.IEventoRepository;

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

//    public Page<EventoResponseDTO> findAllEvents(Integer pageNumber, Integer pageSize, String sortBy, String sortDir){
//        PageRequest page = PageRequest.of(
//                pageNumber,
//                pageSize,
//                Sort.by(Sort.Direction.fromString(sortDir), sortBy)
//        );
//
//        Page<Evento> eventsPage = eventoRepository.findAll(page);
//        var results = eventsPage.map(EventoService::toDTO);
//
//        return results;
//    }

//    // TODO: recuperar evento por id
//    public EventoResponseDTO findById(Integer idEvento){
//        var event =  eventoRepository.findById(idEvento).orElseThrow(() -> new RuntimeException("Erro"));
//        return toDTO(event);
//    }

//
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



}
