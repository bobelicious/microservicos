package com.augusto.address.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.augusto.address.model.Address;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByUserId(Long id);
    Optional<Address> findByCep(String cep);
}
