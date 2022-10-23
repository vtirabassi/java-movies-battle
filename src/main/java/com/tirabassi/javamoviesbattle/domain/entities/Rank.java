package com.tirabassi.javamoviesbattle.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Rank")
@NoArgsConstructor
@AllArgsConstructor
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @Column
    private Integer gamesPlayed = 0;

    @Column
    private Integer answerCorrects = 0;

    @Column
    private boolean inGame = false;
}
