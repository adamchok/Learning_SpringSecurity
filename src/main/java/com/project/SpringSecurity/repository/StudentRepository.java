package com.project.SpringSecurity.repository;

import com.project.SpringSecurity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByName(String name);
}
