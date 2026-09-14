package br.com.alphacoach.app.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
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
    //@Column(name = "valorPago")
    //private Float valorPago;
    @ManyToOne
    @JoinColumn(name = "planoId", nullable = false)
    private Planos plano;
    //@Column(name = "formaPagamento")
    //@Enumerated(EnumType.STRING)
    //private FormasTypes formaPagamento;
    //@Column(name = "totalPago")
    //private Float totalPago;
    //@Column(name = "proximoPagamento")
    //@JsonFormat(pattern = "dd/MM/yyyy")
    //private List<LocalDate> proximoPagamento;
   // @Column(name = "pagamentosEfetuados")
    //@JsonFormat(pattern = "dd/MM/yyyy")
    //private List<LocalDate> pagamentosEfetuados;
    //Criar uma outra classe no estilo de treinos com exercicios treinos, mas sendo PagamentoAluno com StatusPagamento


    public PagamentoAluno(Long id, Aluno aluno, LocalDate competencia, LocalDate dataPagamento, Planos plano) {
        this.id = id;
        this.aluno = aluno;
        this.competencia = competencia;
        this.dataPagamento = dataPagamento;
        this.plano = plano;
    }

    public PagamentoAluno() {
    }

}
