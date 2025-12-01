package br.org.ministerioatos.calendarAPI;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.org.ministerioatos.calendarAPI.module.Local.model.Local;
import br.org.ministerioatos.calendarAPI.module.Local.service.ViacepValidatorService;

class ViacepValidatorServiceTests {

	private ViacepValidatorService service = new ViacepValidatorService();

	@Test
	@DisplayName("Requisita CEP")
	void requestCEP_isSucessScenario() {
		var cep = "29047-062";
		var data = service.requestCEP(cep);

		assertTrue(data instanceof String);
		assertFalse(data.isEmpty());
		assertTrue(data.contains("\"cep\":"));
		assertTrue(data.contains("\"logradouro\":"));
		assertTrue(data.contains("\"bairro\":"));
		assertTrue(data.contains(cep));
	}

	@Test
	@DisplayName("Requisita CEP, falha e lança uma exceção")
	void requestCEP_isFail() {
		var cep = "";
		assertThrows(RuntimeException.class, () -> {
			service.requestCEP(cep);
		});
	}

	// Validar a passagem de cep tanto com - quanto sem hifen

	// @Test
	// @DisplayName("Desserializa um json de local recebido")
	// void desserializeCEPtoLocal() throws JsonMappingException, JsonProcessingException{
	// 	var CEP = "{ \"cep\": \"29047-062\", \"logradouro\": \"Avenida Professor Hermínio Blackman\", \"complemento\": \"\", \"unidade\": \"\", \"bairro\": \"Bonfim\", \"localidade\": \"Vitória\", \"uf\": \"ES\", \"estado\": \"Espírito Santo\", \"regiao\": \"Sudeste\", \"ibge\": \"3205309\", \"gia\": \"\", \"ddd\": \"27\", \"siafi\": \"5705\" }";
	// 	Local CEPtoLocal = service.desserializeCEPtoLocal(CEP); 
	// 	assertNotNull(CEPtoLocal);
	// 	assertFalse(CEPtoLocal.getRua().isEmpty());
	// 	assertTrue(CEPtoLocal.getNumero() instanceof Integer);
	// 	assertFalse(CEPtoLocal.getBairro().isEmpty());
	// 	assertFalse(CEPtoLocal.getCidade().isEmpty());
	// 	assertTrue(CEPtoLocal.getCEP() == "29047-062");
	// 	assertFalse(CEPtoLocal.getUF().isEmpty());
	// }
}
