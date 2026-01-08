package br.org.ministerioatos.calendarAPI.infrastructure.data.repository;

import br.org.ministerioatos.calendarAPI.infrastructure.data.models.UserDataJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserDataJpa, Integer> {

    UserDetails findByLogin(String login);

    boolean existsByLogin(String login);
}
