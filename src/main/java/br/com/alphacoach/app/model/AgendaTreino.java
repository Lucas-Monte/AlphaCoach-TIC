package br.com.alphacoach.app.model;

import br.com.alphacoach.app.exception.BusinessException;
import br.com.alphacoach.app.exception.CheckInAntesDoTempoException;
import br.com.alphacoach.app.exception.CheckInDepoisDoTempoException;
import br.com.alphacoach.app.exception.CheckInDuplicadoException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


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
    private Boolean checkIn = false;

    public AgendaTreino(Long id, Aluno aluno, LocalDateTime data) {
        this.id = id;
        this.aluno = aluno;
        this.data = data;
        this.checkIn = false;
    }

    public AgendaTreino() {
    }

    public Boolean realizarCheckIn(){
        if(this.checkIn) {
            throw new CheckInDuplicadoException();
        }
        if (LocalDateTime.now().isAfter(this.data.plusMinutes(30))) {
            throw new CheckInDepoisDoTempoException();
        }
        if (LocalDateTime.now().isBefore(this.data.minusMinutes(30))) {
            throw new CheckInAntesDoTempoException();
        }
        return this.checkIn = true;
    }

    public void cancelarCheckIn() {
        if (!this.checkIn) {
            throw new BusinessException("Check-in não realizado ainda");
        }
        this.checkIn = false;
    }

    public LocalDateTime reagendar(LocalDateTime novaData) {
        if (novaData.isBefore(LocalDateTime.now())) {
            throw new BusinessException("Impossível alterar para uma data que ja passou!");
        }

        return this.data = novaData;
    }


    //realizarCheckIn(): marca checkIn = true, bloqueia check-in duplicado e, se quiser, fora de uma janela de tempo (ex.: 30 minutos antes do treino).
    //cancelar() e reagendar(LocalDateTime nova)
}
