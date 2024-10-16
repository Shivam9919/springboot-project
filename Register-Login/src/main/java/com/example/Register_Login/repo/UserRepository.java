package com.example.Register_Login.repo;

import com.example.Register_Login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    List<User> findByEmail(@Param("email") String email);

    User findByEmail(String email);

    boolean existsByEmail(String email);

}
