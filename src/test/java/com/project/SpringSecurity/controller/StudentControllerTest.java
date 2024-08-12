package com.project.SpringSecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.SpringSecurity.model.Student;
import com.project.SpringSecurity.service.JwtService;
import com.project.SpringSecurity.service.StudentService;
import com.project.SpringSecurity.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class StudentControllerTest {
    @Autowired
    private final MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    public StudentControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String mapToJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    @ParameterizedTest
    @MethodSource("mockStudentProviderNotNull")
    @WithMockUser(roles = {"ADMIN"})
    void testGetStudentByNameNotNull(Student mockStudent) throws Exception {
        Mockito.when(studentService.getStudentByName(mockStudent.getName())).thenReturn(mockStudent);
        String inputJson = mapToJson(mockStudent);
        String URI = "/students/student";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .param("name", mockStudent.getName())
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputJson = response.getContentAsString();
        assertEquals(inputJson, outputJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private static Stream<Arguments> mockStudentProviderNotNull() {
        Student mockStudent_1 = Student.builder()
                .id(UUID.fromString("da5979d4-962c-4a6f-b75f-7183a7fbbdeb"))
                .name("Adam Chok")
                .marks(90)
                .roles(List.of("STUDENT")).build();
        Student mockStudent_2 = Student.builder()
                .id(UUID.fromString("b769dee1-fff1-488c-b284-0828ec092724"))
                .name("Jade Chan")
                .marks(80)
                .roles(List.of("STUDENT")).build();
        return Stream.of(
                Arguments.of(mockStudent_1),
                Arguments.of(mockStudent_2)
        );
    }

    @ParameterizedTest
    @MethodSource("nameProviderNull")
    @WithMockUser(roles = {"ADMIN"})
    void testGetStudentByNameNull(String name) throws Exception {
        String URI = "/students/student";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(URI)
                .param("name", name)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    private static Stream<Arguments> nameProviderNull() {
        return Stream.of(
                Arguments.of("Jeff Bezos"),
                Arguments.of("Katty Parry"),
                Arguments.of("Ariana Grande")
        );
    }
}
