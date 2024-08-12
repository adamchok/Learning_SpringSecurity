package com.project.SpringSecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="student")
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
    @Column(name="marks")
    private int marks;

    @NotNull
    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
