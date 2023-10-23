package com.augusto.authservice.payload;


import com.augusto.authservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    // private Long id;
    private String name;
    private String email;
    private String environment;
    private String cpf;
    private String password;
    private Long customId;

    public UserDto (User user){
        // this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.customId = user.getCustomId();
    }
}
