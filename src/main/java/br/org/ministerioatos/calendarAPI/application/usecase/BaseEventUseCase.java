package br.org.ministerioatos.calendarAPI.application.usecase;

import br.org.ministerioatos.calendarAPI.application.mappers.EventoDataMapper;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.IEventoRepository;

public abstract class BaseEventUseCase {
    protected IEventoRepository repository;
    protected EventoDataMapper mapper;

    public BaseEventUseCase(IEventoRepository repository, EventoDataMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

}
