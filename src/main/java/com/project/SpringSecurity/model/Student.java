package com.project.SpringSecurity.model;

import com.project.SpringSecurity.validation.RolesMustInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Min(0)
    @Max(100)
    @Column(name = "marks")
    private int marks;

    @NotNull
    @Column(name = "roles")
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    @RolesMustInclude(elements = {"STUDENT"})
    private List<String> roles;

    // @Email
    // https://jakarta.ee/specifications/bean-validation/3.0/apidocs/jakarta/validation/constraints/package-summary
}
