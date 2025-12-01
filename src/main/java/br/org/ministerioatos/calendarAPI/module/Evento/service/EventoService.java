package br.org.ministerioatos.calendarAPI.module.Evento.service;

import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request.EventoRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.SubEventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.model.Evento;
import br.org.ministerioatos.calendarAPI.module.Evento.model.SubEvento;
import br.org.ministerioatos.calendarAPI.module.Evento.repository.IEventoRepository;
import br.org.ministerioatos.calendarAPI.module.Local.model.Local;
import br.org.ministerioatos.calendarAPI.module.Local.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private IEventoRepository eventoRepository;

    @Autowired
    private LocalService localService;
    
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

        // Definindo valores para os campos nullable
        var descricao = "";
        var dataHoraFim = evento.dataInicio().plusMinutes(30);
        var subEventos = evento.subEventos();
        var local = resolverLocal(evento);
        
        if (evento.descricao().isPresent()){
            descricao = evento.descricao().get();
        }

        // Caso não seja fornecida uma data de fim, definir a data de início como data de fim + 30 minutos
        if (evento.dataFim().isPresent()){
            dataHoraFim = evento.dataFim().get();
        }

        var newEvent = Evento.builder()
                .titulo(evento.titulo())
                .descricao(descricao)
                .dataHoraInicio(evento.dataInicio())
                .dataHoraFim(dataHoraFim)
                .local(local)
                .build();

        if (!subEventos.isEmpty()){
            var subEventosList = subEventos.stream().map(subEvento -> {
                var novoSubEvento = new SubEvento();
                novoSubEvento.setTitulo(subEvento.titulo());
                novoSubEvento.setDataInicio(subEvento.dataInicio());
                novoSubEvento.setEvento(newEvent);

                return novoSubEvento;
            }).collect(Collectors.toList());

            newEvent.setSubEventos(subEventosList);
        } else {
            newEvent.setSubEventos(List.of());
        }

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
            return eventIsDeleted = true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao deletar o evento");
        }
    }

    private Local resolverLocal(EventoRequestDTO evento){
        if (evento.idLocalExistente().isPresent()){
            return localService.findById(evento.idLocalExistente().get());
        } else if (evento.local().isPresent()){
            return localService.createLocalIfNotExists(evento.local().get());
        } else {
            throw new RuntimeException("É necessário informar um local para o evento");
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
