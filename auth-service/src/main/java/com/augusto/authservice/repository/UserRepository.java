/* */
package com.augusto.authservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.augusto.authservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailOrCpf(String email, String cpf);
}
