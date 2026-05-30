package com.hotelbooking.system.Repository;

import com.hotelbooking.system.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
