package com.project.SpringSecurity.service;

import com.project.SpringSecurity.model.Student;
import com.project.SpringSecurity.model.User;
import com.project.SpringSecurity.repository.StudentRepository;
import com.project.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(int pageNo, int pageSize) {
        Pageable page = PageRequest.of(pageNo, pageSize).withSort(Sort.by("name").ascending());
        Page<Student> students = studentRepository.findAll(page);
        return students.getContent();
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentById(String id) {
        Optional<Student> opt = studentRepository.findById(UUID.fromString(id));
        return opt.orElse(null);
    }

    public Student getStudentByName(String name) {
        return studentRepository.findByName(name);
    }
}
