package ru.kpfu.itis.gnt.registration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.gnt.registration.data.repository.user.UserRepositoryImpl;
import ru.kpfu.itis.gnt.registration.dto.LoginDto;
import ru.kpfu.itis.gnt.registration.entity.UserEntity;
import ru.kpfu.itis.gnt.registration.entity.UserRole;
import ru.kpfu.itis.gnt.registration.exceptions.DBException;
import ru.kpfu.itis.gnt.registration.dto.UserDto;
import ru.kpfu.itis.gnt.registration.exceptions.EmptyResultDBException;
import ru.kpfu.itis.gnt.registration.mapper.UserMapper;
import ru.kpfu.itis.gnt.registration.utils.enums.ErrorEnum;

@Service
public class UserService {

    private final UserRepositoryImpl userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepositoryImpl userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(UserDto userDto) throws DBException {

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity user = userMapper.userDtoToUserEntity(userDto);
        user.setRole(UserRole.USER);
        return userRepository.saveUser(user);

    }

    public UserDto login(LoginDto loginDto) throws DBException {
        UserEntity rawUser = userRepository.findUserByEmailAndPassword(
                loginDto.getEmail(),
                passwordEncoder.encode(loginDto.getPassword())
        );
        return userMapper.userEntityToUserDto(rawUser);
    }
}
