package com.augusto.user.payload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDto {
    private String name;
    private String email;
    private String cpf;
    private String password;
    private String roles;
    private Long customId;
}

