package com.fields.fileds_library.domain.user;

import com.fields.fileds_library.model.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserDto createUser(UserDto userDto);

    void deleteUser(UUID id);

    List<UserDto> findAllUsers(String userName);

    Optional<UserDto> findUserById(UUID id);

    UserDto updateUser(UUID id, UserDto userDto);

}
