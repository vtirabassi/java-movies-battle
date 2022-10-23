package com.tirabassi.javamoviesbattle.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(nullable = false)
    @NotEmpty(message = "{usuario.login.obrigatorio}")
    private String login;

    @Column(nullable = false)
    @NotEmpty(message = "{usuario.senha.obrigatorio}")
    private String password;

    @Column
    private Boolean admin = false;

    @OneToOne(mappedBy = "user")
    private Rank rank;
}
