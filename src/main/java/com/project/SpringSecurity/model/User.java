package com.project.SpringSecurity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "username")
    @NotNull
    private String username;

    @Column(name = "password")
    @NotNull
    private String password;

    /*
    @ElementCollection annotation is one of the annotation of JPA. This can be used with Collection of Embeddable Class. @ElementCollection has fetch attributes. fetch attribute supports Eager and Lazy types.
        1) FetchType.EAGER: Eager fetch type fetches the data with master other datas.
        2) FetchType.LAZY: Lazy fetch type fetches the data whenever call getter method of the object.
    */
    @Column(name = "roles")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
