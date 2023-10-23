package com.augusto.user.payload;
import java.util.List;
import com.augusto.user.model.User;
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
    private List<ProductDto> product;
    private String environment;
    private String cpf;
    private Long customId;

    public UserDto (UserInputDto user){
        // this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.customId = user.getCustomId();
    }

        public UserDto (User user){
        // this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.customId = user.getCustomId();
    }
}
