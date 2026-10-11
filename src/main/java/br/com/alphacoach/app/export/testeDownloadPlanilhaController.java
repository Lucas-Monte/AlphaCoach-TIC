package br.com.alphacoach.app.export;

import br.com.alphacoach.app.model.Matricula;
import br.com.alphacoach.app.repository.AlunoRepository;
import br.com.alphacoach.app.repository.MatriculaRepository;
import br.com.alphacoach.app.repository.PlanosRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class testeDownloadPlanilhaController {
    MatriculaRepository matriculaRepository;
    AlunoRepository alunoRepository;
    PlanosRepository planosRepository;

    public testeDownloadPlanilhaController(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository, PlanosRepository planosRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.planosRepository = planosRepository;
    }


    @GetMapping("/relatorio")
    public ResponseEntity<byte[]> gerarRelatorio() throws IOException {
        List<Matricula> matriculas = matriculaRepository.findAll();

        try (Workbook wb = new XSSFWorkbook();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) { //armazenamento temporario

            Sheet sheet = wb.createSheet("Matriculas");

            // Cabeçalho
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Matricula");
            header.createCell(1).setCellValue("Valor");

            // Dados
            int linha = 1;
            for (Matricula v : matriculas) {
                Row row = sheet.createRow(linha++);
                row.createCell(0).setCellValue(v.getAluno().getNome());
                row.createCell(1).setCellValue(v.getPlano().getValor().doubleValue());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            wb.write(buffer);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.xlsx")//Content-Disposition: attachment instrui o navegador a baixar o arquivo em vez de tentar exibi-lo, e filename define o nome sugerido.
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    //contentType informa que o conteúdo é um .xlsx.
                    .body(buffer.toByteArray()); //out.toByteArray() entrega os bytes do arquivo como corpo da resposta.
        }

    }
}
