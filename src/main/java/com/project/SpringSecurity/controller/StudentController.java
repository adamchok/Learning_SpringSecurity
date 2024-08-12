package com.project.SpringSecurity.controller;

import com.project.SpringSecurity.model.Student;
import com.project.SpringSecurity.model.StudentDTO;
import com.project.SpringSecurity.service.StudentService;
import com.project.SpringSecurity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Student> getStudents(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return studentService.getStudents(pageNumber, pageSize);
    }

    @GetMapping("/student")
    public ResponseEntity<Student> getStudentByName(@RequestParam(value = "name") String name) {
        Student student = studentService.getStudentByName(name);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UUID> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        userService.createUser(studentDTO.getUser());
        return ResponseEntity.ok().body(studentService.createStudent(studentDTO.getStudent()).getId());
    }
}
