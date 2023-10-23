package com.augusto.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.augusto.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdOrEmailOrCpf(Long id, String email, String cpf);
    Optional<User> findByEmailOrCpf(String email, String cpf);
}
