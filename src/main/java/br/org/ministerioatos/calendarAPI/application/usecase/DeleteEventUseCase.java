package br.org.ministerioatos.calendarAPI.application.usecase;

import br.org.ministerioatos.calendarAPI.application.mappers.EventoDataMapper;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.IEventoRepository;

public class DeleteEventUseCase extends BaseEventUseCase{
    public DeleteEventUseCase(IEventoRepository repository, EventoDataMapper mapper) {
        super(repository, mapper);
    }
}
