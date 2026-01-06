package br.org.ministerioatos.calendarAPI.infrastructure.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AdressDataJpa {
    @Column(name = "rua")
    private String street;

    @Column(name = "numero")
    private Integer number;

    @Column(name = "bairro")
    private String neighborhood;

    @Column(name = "cidade")
    private String city;

    @Column(name = "uf")
    private String state;

    @Column(name = "cep")
    private String zipCode;


    public AdressDataJpa(
            String street,
            Integer number,
            String neighborhood,
            String city,
            String name,
            String zipCode
    ) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = name;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }
}
