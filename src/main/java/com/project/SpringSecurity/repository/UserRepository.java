package com.project.SpringSecurity.repository;

import com.project.SpringSecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    //@Query("SELECT u FROM User u ORDER BY u.id ASC") - THIS IS IN JPQL DIALECT

    // ADD NATIVE QUERY = TRUE TO ACCEPT SQL QUERIES INPUT
    @Query(value = "SELECT * FROM users ORDER BY username ASC", nativeQuery = true)
    List<User> findAllSortedByUsername();
}
