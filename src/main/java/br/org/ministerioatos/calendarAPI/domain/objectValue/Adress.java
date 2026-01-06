package br.org.ministerioatos.calendarAPI.domain.objectValue;

import br.org.ministerioatos.calendarAPI.domain.State;

public class Adress {
    private String street;
    private Integer number;
    private String neighborhood;
    private String city;
    private State state;
    private String zipCode;

    public Adress(
            String street,
            Integer number,
            String neighborhood,
            String city,
            State state,
            String zipCode
    ) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
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

    public State getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }
}
