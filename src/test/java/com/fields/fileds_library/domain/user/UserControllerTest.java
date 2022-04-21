package com.fields.fileds_library.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fields.fileds_library.model.Role;
import com.fields.fileds_library.model.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    private final String userName = "admin";
    private final SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    private UserDto userDto;
    private List<User> users;

    @BeforeAll
    void setUp() {
        users = createUsers();
        userDto = createUserDto();

    }

    @AfterAll
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("POST - /users - OK")
    void createUser_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.userName", is(userDto.getUserName())))
                .andExpect(jsonPath("$.roles", is(userDto.getRoles().stream()
                        .map(Role::getValue).collect(Collectors.toList()))))
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE - /users/{id} - OK")
    void deleteUser_ShouldReturnOk() throws Exception{
        mockMvc.perform(delete("/users/{id}", users.get(users.size()-1).getId())
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET - /users - OK")
    void findAllUsers_ShouldReturnOK() throws Exception{
        int noOfDbRecords = userRepository.findAll().size();
        mockMvc.perform(get("/users")
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(noOfDbRecords)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET - /user/{id} - OK")
    void findUserById_ShouldReturnOk() throws Exception{
        mockMvc.perform(get("/users/{id}", users.get(0).getId())
                        .with(SecurityMockMvcRequestPostProcessors
                                .user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.email", is(users.get(0).getEmail())))
                .andExpect(jsonPath("$.userName", is(users.get(0).getUserName())))
                .andExpect(jsonPath("$.roles", is(users.get(0).getRoles().stream()
                        .map(Role::getValue).collect(Collectors.toList()))))
                .andDo(print());
    }

    @Test
    @DisplayName("PUT - /users/{id} - OK")
    void updateUser_ShouldReturnOk() throws Exception{
        mockMvc.perform(put("/users/{id}", users.get(3).getId())
                        .content(objectMapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.
                                user(userName)
                                .authorities(authority)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.email", is(userDto.getEmail())))
                .andExpect(jsonPath("$.userName", is(userDto.getUserName())))
                .andExpect(jsonPath("$.roles", is(userDto.getRoles().stream()
                        .map(Role::getValue).collect(Collectors.toList()))))
                .andDo(print());
    }

    private UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail(users.get(0).getEmail());
        userDto.setUserName(users.get(0).getUserName());
        userDto.setPassword(users.get(0).getPassword());
        userDto.setRoles(users.get(0).getRoles());
        return userDto;
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            User user = new User("email" + i, "userName"+i, "password"+i, List.of(Role.ADMIN));
            userRepository.save(user);
            users.add(user);
        }
        return users;
    }
}