package br.com.alphacoach.app.model;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="pagamentoAluno", uniqueConstraints = {
        @UniqueConstraint(
                name = "un_aluno_competencia",
                columnNames = {"alunoId", "competencia"})
})
public class PagamentoAluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "alunoId", nullable = false)
    private Aluno aluno;
    @Column(name = "competencia", nullable = false)
    private LocalDate competencia;
    @Column(name = "dataPagamento", nullable = false)
    private LocalDate dataPagamento;
    @ManyToOne
    @JoinColumn(name = "planoId", nullable = false)
    private Planos plano;


    public PagamentoAluno(Long id, Aluno aluno, LocalDate competencia, LocalDate dataPagamento, Planos plano) {
        this.id = id;
        this.aluno = aluno;
        this.competencia = competencia;
        this.dataPagamento = dataPagamento;
        this.plano = plano;
    }

    public PagamentoAluno() {
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

    public LocalDate getCompetencia() {
        return competencia;
    }

    public void setCompetencia(LocalDate competencia) {
        this.competencia = competencia;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Planos getPlano() {
        return plano;
    }

    public void setPlano(Planos plano) {
        this.plano = plano;
    }
}
