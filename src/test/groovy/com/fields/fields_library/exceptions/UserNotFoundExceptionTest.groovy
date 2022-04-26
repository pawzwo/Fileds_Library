package com.fields.fields_library.exceptions

import com.fields.fields_library.domain.user.User
import com.fields.fields_library.domain.user.UserController
import com.fields.fields_library.domain.user.UserRepository
import com.fields.fields_library.model.Role
import com.fields.fields_library.model.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserNotFoundExceptionTest extends Specification {

    @Autowired
    private UserRepository userRepository
    @Autowired
    private UserController userController

    private UserDto userDto
    private final UUID INVALID_ID = UUID.randomUUID()

    void setup() {
        User user = new User("email", "userName", "password", List.of(Role.ADMIN))
        userDto = userRepository.save(user).toDto()
    }

    void cleanup() {
        userRepository.deleteAll()
    }

    def "Try to find user with wrong id and get thrown UserNotFoundException"() {
        when:
        userController.findUserById(INVALID_ID)
        then:
        thrown(UserNotFoundException)
    }

    def "Try to delete non existing user and get thrown UserNotFoundException"() {
        when:
        userController.deleteUser(INVALID_ID)
        then:
        thrown(UserNotFoundException)
    }

    def "Try to update non existing user and get thrown UserNotFoundException"() {
        when:
        userController.updateUser(INVALID_ID, userDto)
        then:
        thrown(UserNotFoundException)
    }
}
