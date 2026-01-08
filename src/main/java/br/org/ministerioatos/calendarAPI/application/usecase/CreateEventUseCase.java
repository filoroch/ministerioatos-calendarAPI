package br.org.ministerioatos.calendarAPI.application.usecase;
import br.org.ministerioatos.calendarAPI.application.DTO.event.CreateEventInput;
import br.org.ministerioatos.calendarAPI.application.mappers.EventoDataMapper;
import br.org.ministerioatos.calendarAPI.application.DTO.event.EventOutput;
import br.org.ministerioatos.calendarAPI.domain.exceptions.evento.EventAlredyExists;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.IEventoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateEventUseCase extends BaseEventUseCase{
    public CreateEventUseCase(IEventoRepository repository, EventoDataMapper mapper) {
        super(repository, mapper);
    }

    @Transactional
    public EventOutput execute(CreateEventInput input) {
        if (repository.existsByTitleAndStartDateTime(input.title(), input.startDateTime())) {
            throw new EventAlredyExists("Já existe um evento com o mesmo título e data/hora de início.");
        }

        var domain = mapper.toDomain(input);

        if (repository.existsByStartDateTime(input.startDateTime())) {
            domain.resolveConflictsData();
        }

        var dataEntity = mapper.toDataJpa(domain);
        var savedEntity = repository.saveAndFlush(dataEntity);
        return mapper.toOutput(savedEntity);
    }
}
