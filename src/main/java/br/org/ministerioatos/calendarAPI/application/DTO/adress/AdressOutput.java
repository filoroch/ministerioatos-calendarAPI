package br.org.ministerioatos.calendarAPI.application.DTO.adress;

public record AdressOutput(
    String street,
    Integer number,
    String neighborhood,
    String city,
    String state,
    String zipCode
){
}
