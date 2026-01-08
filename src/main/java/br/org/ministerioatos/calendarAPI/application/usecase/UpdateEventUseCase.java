package br.org.ministerioatos.calendarAPI.application.usecase;

import br.org.ministerioatos.calendarAPI.application.mappers.EventoDataMapper;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.IEventoRepository;

public class UpdateEventUseCase extends BaseEventUseCase {
    public UpdateEventUseCase(IEventoRepository repository, EventoDataMapper mapper) {
        super(repository, mapper);
    }
}
