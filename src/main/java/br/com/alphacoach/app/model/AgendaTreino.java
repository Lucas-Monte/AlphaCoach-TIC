package br.com.alphacoach.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table (name = "agendaAluno")
public class AgendaTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="alunoId")
    @JsonIgnoreProperties("agenda")
    private Aluno aluno;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime data;
    @Column
    private Boolean checkIn;

    public AgendaTreino(Long id, Aluno aluno, LocalDateTime data) {
        this.id = id;
        this.aluno = aluno;
        this.data = data;
        this.checkIn = false;
    }

    public AgendaTreino() {
    }
}
