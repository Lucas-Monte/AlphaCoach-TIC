package br.com.alphacoach.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
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
    private LocalDate data;
    @Column
    private Boolean checkIn;

    public AgendaTreino(Long id, Aluno aluno, LocalDate data) {
        this.id = id;
        this.aluno = aluno;
        this.data = data;
        this.checkIn = false;
    }

    public AgendaTreino() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }

    public boolean fazerCheckIn() {
        return this.checkIn = true;
    }

    public boolean removerCheckIn() {
        return this.checkIn = false;
    }
}
