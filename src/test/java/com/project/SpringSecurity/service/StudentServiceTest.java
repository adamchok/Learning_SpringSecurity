package com.project.SpringSecurity.service;

import com.project.SpringSecurity.model.Student;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class StudentServiceTest {
    private final StudentService studentService;

    @Autowired
    public StudentServiceTest(StudentService studentService) {
        this.studentService = studentService;
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("pageProvider")
    void testGetStudents(int pageNo, int pageSize) {
        List<Student> students = studentService.getStudents(pageNo, pageSize);
        assertNotNull(students, "The student list should not be null");
        assertTrue(students.size() <= pageSize, "The size of the student list should not exceed pageSize");
    }

    private static Stream<Arguments> pageProvider() {
        return Stream.of(
                Arguments.of(0, 10),
                Arguments.of(1, 5),
                Arguments.of(2, 20)
        );
    }

    @ParameterizedTest
    @Order(2)
    @MethodSource("nameProviderNoRecord")
    void testGetStudentByNameNoRecord(String name) {
        Student student = studentService.getStudentByName(name);
        assertNull(student);
    }

    private static Stream<Arguments> nameProviderNoRecord() {
        return Stream.of(
                Arguments.of("Jason Jackson"),
                Arguments.of("Marry Williams"),
                Arguments.of("Sandy Sanders")
        );
    }

    @ParameterizedTest
    @Order(3)
    @MethodSource("nameProviderHaveRecord")
    void testGetStudentByNameHaveRecord(String name) {
        Student student = studentService.getStudentByName(name);
        assertNotNull(student);
    }

    private static Stream<Arguments> nameProviderHaveRecord() {
        return Stream.of(
                Arguments.of("Adam Chok"),
                Arguments.of("Jade Chan")
        );
    }
}
