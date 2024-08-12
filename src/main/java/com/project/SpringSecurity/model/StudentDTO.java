package com.project.SpringSecurity.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDTO {

    private Student student;
    private User user;
}
