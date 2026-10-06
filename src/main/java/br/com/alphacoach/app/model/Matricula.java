package br.com.alphacoach.app.model;

import br.com.alphacoach.app.exception.BusinessException;
import br.com.alphacoach.app.model.enums.StatusMatricula;
import br.com.alphacoach.app.model.enums.StatusParcela;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "matricula", uniqueConstraints = {
        @UniqueConstraint(
                name="un_aluno_plano_status",
                columnNames = {"aluno_id", "plano_id", "status_maticula"}
        )
})
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "plano_id")
    private Planos plano;
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;
    @Column(name = "status_matricula")
    @Enumerated(EnumType.STRING)
    private StatusMatricula statusMatricula;
    @Column(name = "data_inicio")
    private LocalDate dataInicio;
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Parcela> parcelas = new ArrayList<>();

    public Matricula(Long id, Planos plano, Aluno aluno, StatusMatricula statusMatricula, LocalDate dataInicio) {
        this.id = id;
        this.plano = plano;
        this.aluno = aluno;
        this.statusMatricula = statusMatricula;
        this.dataInicio = dataInicio;
    }

    public Matricula() {
    }

    public void adicionarParcela(Parcela parcela) {
        parcela.setMatricula(this);
        parcelas.add(parcela);
    }

    public void cancelarMatricula() {
        if (this.statusMatricula.equals(StatusMatricula.CANCELADO)) {
            throw new BusinessException("Matricula ja cancelada");
        }
        for (Parcela parcela : parcelas) {
            if (parcela.estaEmAberto()) {
                parcela.cancelar();
            }
        }

        this.statusMatricula = StatusMatricula.CANCELADO;
    }

    //adicionarParcela(Parcela): faz setMatricula(this) e adiciona na lista.
    //cancelar(): muda o status para cancelada e cancela as parcelas ainda em aberto. Pode bloquear o cancelamento se a matrícula já estiver cancelada.
}
