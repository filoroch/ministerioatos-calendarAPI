package br.org.ministerioatos.calendarAPI.module.Local.service;

import br.org.ministerioatos.calendarAPI.module.Local.DTO.LocalRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Local.model.Local;
import br.org.ministerioatos.calendarAPI.module.Local.repository.ILocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalService {

    @Autowired
    private ILocalRepository repository;

    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    public Local findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Local não encontrado com ID: " + id));
    }

    @Transactional
    public Local createLocalIfNotExists(LocalRequestDTO local) {

        // Verificar tambem se foi passado um CEP pois assim, poderia buscar o endereço diretamente no VIACEP e validar somente os campos faltantes
        // Gerar um outro if que busque o endereço parcial e complete os campos faltantes

        var newLocal = Local.builder()
                .rua(local.rua())
                .numero(local.numero())
                .complemento(local.complemento())
                .cidade(local.cidade())
                .bairro(local.bairro())
                .CEP(local.CEP())
                .UF(local.UF())
                .build();

        var saveLocal = repository.saveAndFlush(newLocal);
        return saveLocal;
    }
    //public Local createLocalByCEP(){}
}


