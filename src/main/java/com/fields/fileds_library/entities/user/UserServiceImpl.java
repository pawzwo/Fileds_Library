package com.fields.fileds_library.entities.user;

import com.fields.fileds_library.exceptions.UserNotFoundException;
import com.fields.fileds_library.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User.UserBuilder userBuilder = new User.UserBuilder();
        User user = userBuilder
                .userName(userDto.getUserName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(userDto.getRoles())
                .build();
        return userRepository.save(user).toDto();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public List<UserDto> findAllUsers(String userName) {
        return userRepository.findAllUsersByUsername(userName)
                .stream().map(User::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findUserById(UUID id) {
        return userRepository.findById(id).map(User::toDto);
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.updateUser(userDto);
        return userRepository.save(user).toDto();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(UserNotFoundException::new);
        Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role->new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }
}
