package br.org.ministerioatos.calendarAPI.Evento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String rua;
    private Integer numero;
    private String complemento;
    private String cidade;
    private String CEP;
    private String UF;
}
