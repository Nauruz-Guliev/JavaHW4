package ru.kpfu.itis.gnt.registration.data.repository.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.gnt.registration.entity.UserEntity;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;
import ru.kpfu.itis.gnt.registration.utils.enums.ErrorEnum;

import javax.sql.DataSource;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository<UserEntity> {


    private final JdbcTemplate jdbcTemplate;
    //language=SQL
    private static final String SQL_FIND_USER = "SELECT * FROM \"user\" WHERE email = ? AND password = ?;";

    //language=SQL
    private static final String SQL_DELETE_USER = "DELETE from \"user\" where id = ?";
    //language=SQL
    private static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM \"user\" where email=?";
    //language=SQL
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM \"user\" where id=?";
    //language=SQL
    private static final String SQL_INSERT_USER = "INSERT INTO \"user\"(role, firstname, lastname, email, password, birthdate, country) VALUES (?, ?, ?,?,?,TO_DATE(?, 'YYYYMMDD'), ?);";


    @Autowired
    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean saveUser(UserEntity user) throws DBException {
        try {
            return jdbcTemplate.update(
                    SQL_INSERT_USER,
                    user.getRole().name(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getBirthDate(),
                    user.getCountry()
            ) > 0;
        } catch (DuplicateKeyException ex) {
            throw new DBException(ErrorEnum.DUPLICATE_USER_FIELDS_EXCEPTION.toString());
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString());
        }
    }

    public UserEntity findById(int id) throws DBException {
        return getUser(SQL_GET_USER_BY_ID, new Object[]{id});
    }

    public Optional<UserEntity> findByEmail(String email) throws DBException {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_USER_BY_EMAIL, new Object[]{email}, userRowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString());
        }
    }

    public UserEntity findUserByEmailAndPassword(String email, String password) throws DBException {
        return getUser(SQL_FIND_USER, new Object[]{email, password});
    }

    private UserEntity getUser(String query, Object[] parameters) throws DBException {
        try {
            return jdbcTemplate.queryForObject(query, parameters, userRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new DBException(ErrorEnum.USER_NOT_FOUND.toString());
        } catch (Exception ex) {
            throw new DBException(ErrorEnum.UNKNOWN_DB_EXCEPTION.toString());
        }
    }

    public boolean deleteUser(int id) {
        return jdbcTemplate.update(SQL_DELETE_USER, id) > 0;
    }


    private final RowMapper<UserEntity> userRowMapper =
            (row, rowNumber) -> {
                UserEntity user = new UserEntity(
                        row.getString("role"),
                        row.getString("firstName"),
                        row.getString("lastName"),
                        row.getString("email"),
                        row.getString("password"),
                        row.getString("birthDate"),
                        row.getString("country")
                );
                user.setId(row.getLong("id"));
                return user;
            };

}
