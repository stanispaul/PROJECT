package com.backendTBD.TBD.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.backendTBD.TBD.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
