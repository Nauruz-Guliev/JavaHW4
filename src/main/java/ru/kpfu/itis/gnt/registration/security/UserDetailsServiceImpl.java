package ru.kpfu.itis.gnt.registration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.gnt.registration.data.repository.user.UserRepository;
import ru.kpfu.itis.gnt.registration.entity.UserEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository<UserEntity> userRepository;

    private final static String DEFAULT_EMAIL_NOT_FOUND_MESSAGE = "Email not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return new UserDetailsImpl(userRepository.findByEmail(email).orElseThrow(
                    () -> {
                        throw new UsernameNotFoundException(DEFAULT_EMAIL_NOT_FOUND_MESSAGE);
                    }
            ));
        } catch (DBException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
