package com.fields.fileds_library.entities.user;

import com.fields.fileds_library.model.Role;
import com.fields.fileds_library.model.UserDto;
import com.fields.fileds_library.entities.ProtoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends ProtoEntity {

    private String email;
    private String userName;
    private String password;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    public UserDto toDto() {
        UserDto userDto = new UserDto();
        userDto.setId(this.getId());
        userDto.email(this.getEmail());
        userDto.userName(this.getUserName());
        userDto.password(this.getPassword());
        userDto.roles(this.getRoles());
        return userDto;
    }

    public User updateUser(UserDto userDto) {
        this.email= userDto.getEmail();
        this.userName = userDto.getUserName();
        this.password = userDto.getPassword();
        this.roles = userDto.getRoles();
        return this;
    }

}
