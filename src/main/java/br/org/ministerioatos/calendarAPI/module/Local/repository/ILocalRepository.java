package br.org.ministerioatos.calendarAPI.module.Local.repository;

import br.org.ministerioatos.calendarAPI.module.Local.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILocalRepository extends JpaRepository<Local, Integer> {
    public boolean existsById(Integer id);

    /**
     * Preciso de um metodo que me permita buscar um endereço parcial e autocompletar os dados faltantes. Ex: <br>
     * - Se eu passar somente o CEP, ele busca o endereço completo, completa campos especificos como nº da rua e complemento e retorna o objeto Local completo. <br>
     * - Se eu passar rua, cidade e estado, ele busca o CEP e completa os campos faltantes. <br>
     * - Se eu passar rua, numero, cidade e estado, ele busca o CEP e completa o complemento. <br>
     * - Se eu passar todos os campos, ele verifica se o endereço existe e retorna o objeto Local completo <br>
     */
    public Local findByRuaAndNumeroAndCidadeAndUF(String rua, Integer numero, String cidade, String UF);

}
