package com.augusto.user.payload;

import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInputDto {
    @NotEmpty(message = "the field name not be empty")
    private String name;
    @NotEmpty(message = "field email not be empty")
    @Email(message = "field EMAIL must be valid")
    private String email;
    @CPF
    @NotEmpty(message = "field CPF not be null")
    private String cpf;
    @NotEmpty(message = "field password not be null")
    @Size(min = 6, message = "password need to be minimum 6 digits")
    private String password;
    @NotEmpty(message = "The field message not be null")
    private String roles;
    private Long customId;
}

