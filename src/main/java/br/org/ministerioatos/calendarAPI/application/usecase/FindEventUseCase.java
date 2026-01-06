package br.org.ministerioatos.calendarAPI.application.usecase;

import br.org.ministerioatos.calendarAPI.application.input.FindEventInput;
import br.org.ministerioatos.calendarAPI.application.mappers.EventoDataMapper;
import br.org.ministerioatos.calendarAPI.application.output.EventOutput;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.EventoSpecification;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.IEventoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.org.ministerioatos.calendarAPI.infrastructure.data.repository.EventoSpecification.isDateInRange;

@Service
public class FindEventUseCase extends BaseEventUseCase{
    public FindEventUseCase(IEventoRepository repository, EventoDataMapper mapper) {
        super(repository, mapper);
    }

    public Page<EventOutput> execute(FindEventInput input, Pageable pageable) {
        var spec = EventoSpecification.containsTitle(input.title())
                .and(isDateInRange(input.startDateTimeRange(), input.endDateTimeRange()));

        var page = repository.findAll(spec, pageable);
        return page.map(mapper::toOutput);
    }
}
