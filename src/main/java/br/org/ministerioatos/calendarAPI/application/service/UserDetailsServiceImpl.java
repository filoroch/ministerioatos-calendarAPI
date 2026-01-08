package br.org.ministerioatos.calendarAPI.application.service;

import br.org.ministerioatos.calendarAPI.application.DTO.auth.RegisterUserDTO;
import br.org.ministerioatos.calendarAPI.domain.exceptions.UsernameAlredyExistsException;
import br.org.ministerioatos.calendarAPI.infrastructure.data.models.UserDataJpa;
import br.org.ministerioatos.calendarAPI.infrastructure.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public UserDataJpa register (RegisterUserDTO dto) {

        if (repository.existsByLogin(dto.login())) {
            throw new UsernameAlredyExistsException("User with login " + dto.login() + " already exists");
        }

        var newUser = UserDataJpa.builder()
                .login(dto.login())
                .password(encoder.encode(dto.password()))
                .build();

        return repository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByLogin(login);
    }
}
