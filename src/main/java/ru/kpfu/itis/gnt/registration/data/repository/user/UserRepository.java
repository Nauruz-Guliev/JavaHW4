package ru.kpfu.itis.gnt.registration.data.repository.user;

import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.gnt.registration.entity.UserEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;

import java.util.Optional;

public interface UserRepository<T> {
    boolean saveUser(T user) throws DBException;
    T findById(int id) throws DBException;
    Optional<T> findByEmail(String email) throws DBException;

    T findUserByEmailAndPassword(String email, String password) throws DBException;
    public boolean deleteUser(int id) throws DBException;

    RowMapper<UserEntity> mapper = null;
}
