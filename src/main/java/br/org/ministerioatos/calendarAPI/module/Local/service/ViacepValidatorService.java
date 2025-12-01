package br.org.ministerioatos.calendarAPI.module.Local.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.org.ministerioatos.calendarAPI.module.Local.model.Local;

public class ViacepValidatorService {
    
    private final ObjectMapper mapper = new ObjectMapper();

    private final String baseUrl = "https://viacep.com.br/ws/";
    //https://viacep.com.br/ws/29047062/json/

    public String requestCEP(String CEP){
        var url = baseUrl + CEP + "/json";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
    HttpResponse<String> response = null;

    try {
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException e) {
        throw new RuntimeException(e);
    } catch (InterruptedException e) {
        throw new RuntimeException(e);
    }

    if (response.statusCode() != 200) {
        throw new RuntimeException("Ocorreu um erro na requisição a " + url + ". ");
    }

    return response.body();
    }

    public Local desserializeCEPtoLocal(String json) throws JsonMappingException, JsonProcessingException{
        return mapper.readValue(json, Local.class);
    }
}
