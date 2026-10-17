package br.com.alphacoach.app.export;

import br.com.alphacoach.app.dto.response.TreinosResponse;
import br.com.alphacoach.app.service.ExercicioTreinoService;
import br.com.alphacoach.app.service.TreinosService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class PlanilhaTreinoDownloadController {
    TreinosService treinosService;
    ExercicioTreinoService exercicioTreinoService;

    public PlanilhaTreinoDownloadController(TreinosService treinosService, ExercicioTreinoService exercicioTreinoService) {
        this.treinosService = treinosService;
        this.exercicioTreinoService = exercicioTreinoService;
    }

    @GetMapping("/treinos/{id}/relatorio/download")
    public ResponseEntity<byte[]> gerarPlanilhaTreino(@PathVariable Long id) throws IOException {
        TreinosResponse response = treinosService.econtrarPorId(id);

        try (Workbook wb = new XSSFWorkbook();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("Treino " + response.treino().getAluno().getNome());
            Row header = sheet.createRow(0);
            //Definindo tamanho da linha
            int pixelsHeader = 42;
            float pontos = pixelsHeader*0.75f;
            header.setHeightInPoints(pontos);
            //criando a celula da header
            Cell cell = header.createCell(0);
            cell.setCellValue("ALPHACOACH - FICHA DE TREINO");
            //Mesclado células
            CellRangeAddress regiao = new CellRangeAddress(0,0,0,8);
            sheet.addMergedRegion(regiao);
            //estilizando a celula
            CellStyle styleHeader = wb.createCellStyle();
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
            styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
            Color azulEscuro = new XSSFColor(new java.awt.Color(31,42,68), null);
            styleHeader.setFillForegroundColor(azulEscuro);
            styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            //estilizando a font
            Font fontHeader = wb.createFont();
            fontHeader.setFontName("Arial");
            fontHeader.setFontHeight((short) 16);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());
            fontHeader.setBold(true);
            //setando a font no estilo
            styleHeader.setFont(fontHeader);
            //setando o estilo para a celula
            cell.setCellStyle(styleHeader);

            //Criando a tabela com informações do aluno
            //criando estilização das celulas de titulo
            CellStyle titlesTableOne = wb.createCellStyle();
            titlesTableOne.setAlignment(HorizontalAlignment.LEFT);
            titlesTableOne.setVerticalAlignment(VerticalAlignment.CENTER);
            Color azulGelo = new XSSFColor(new java.awt.Color(232,236,244), null);
            titlesTableOne.setFillForegroundColor(azulGelo);
            titlesTableOne.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titlesTableOne.setBorderBottom(BorderStyle.THIN);
            titlesTableOne.setBorderTop(BorderStyle.THIN);
            titlesTableOne.setBorderRight(BorderStyle.THIN);
            titlesTableOne.setBorderLeft(BorderStyle.THIN);
            titlesTableOne.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            titlesTableOne.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            titlesTableOne.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            titlesTableOne.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            titlesTableOne.setWrapText(true);
            //Criando font dos titles
            Font fontTitleTableOne = wb.createFont();
            fontTitleTableOne.setBold(true);
            fontTitleTableOne.setFontName("Arial");
            fontTitleTableOne.setColor(IndexedColors.BLACK.getIndex());
            titlesTableOne.setFont(fontTitleTableOne);
            //mesclando celulas de valores
            CellRangeAddress mesclaBDRow3 = new CellRangeAddress(2,2,1,3);
            sheet.addMergedRegion(mesclaBDRow3);
            CellRangeAddress mesclaBDRow4 = new CellRangeAddress(3,3,1,3);
            sheet.addMergedRegion(mesclaBDRow4);
            CellRangeAddress mesclaBDRow5 = new CellRangeAddress(4,4,1,3);
            sheet.addMergedRegion(mesclaBDRow5);
            CellRangeAddress mesclaBDRow6 = new CellRangeAddress(5,5,1,3);
            sheet.addMergedRegion(mesclaBDRow6);
            //estilazando as celulas de resultado
            CellStyle valuesTableOne = wb.createCellStyle();
            valuesTableOne.setAlignment(HorizontalAlignment.LEFT);
            valuesTableOne.setVerticalAlignment(VerticalAlignment.CENTER);
            valuesTableOne.setBorderBottom(BorderStyle.THIN);
            valuesTableOne.setBorderTop(BorderStyle.THIN);
            valuesTableOne.setBorderRight(BorderStyle.THIN);
            valuesTableOne.setBorderLeft(BorderStyle.THIN);
            valuesTableOne.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            valuesTableOne.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            valuesTableOne.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            valuesTableOne.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            //Criando font dos titles
            Font fontValues = wb.createFont();
            fontValues.setFontName("Arial");
            fontValues.setColor(IndexedColors.BLACK.getIndex());
            valuesTableOne.setFont(fontValues);
            //Definindo tamanho da largura da coluna B
            int pixelsB = 170;
            sheet.setColumnWidth(1,pixelsB * 32);
            //Começando pela linha do nome do aluno
            Row aluno = sheet.createRow(2);
            //padronizando nomes de variaveis t(title), v(value)
            Cell tAluno = aluno.createCell(0);
            tAluno.setCellValue("Aluno");
            tAluno.setCellStyle(titlesTableOne);
            Cell vAluno = aluno.createCell(1);
            vAluno.setCellValue(response.treino().getAluno().getNome());
            vAluno.setCellStyle(valuesTableOne);

            //Linha do objetivo
            Row objetivo = sheet.createRow(3);
            Cell tObjetivo = objetivo.createCell(0);
            tObjetivo.setCellValue("Objetivo");
            tObjetivo.setCellStyle(titlesTableOne);

            Cell vObjetivo = objetivo.createCell(1);
            vObjetivo.setCellValue(response.treino().getAluno().getObjetivo());
            vObjetivo.setCellStyle(valuesTableOne);
            //linha do nome do treino
            Row treino = sheet.createRow(4);
            Cell tTreino = treino.createCell(0);
            tTreino.setCellValue("Treino");
            tTreino.setCellStyle(titlesTableOne);

            Cell vTreino = treino.createCell(1);
            vTreino.setCellValue(response.treino().getNome());
            vTreino.setCellStyle(valuesTableOne);

            //linha da data de criação
            Row dataCriacao = sheet.createRow(5);
            Cell tDataCriacao = dataCriacao.createCell(0);
            tDataCriacao.setCellValue("Data de Criação");
            tDataCriacao.setCellStyle(titlesTableOne);

            Cell vDatacriacao = dataCriacao.createCell(1);
            vDatacriacao.setCellValue(response.treino().getDataCriacao());
            //criando estilo da data
            CreationHelper helper = wb.getCreationHelper();
            CellStyle data = wb.createCellStyle();
            data.setAlignment(HorizontalAlignment.LEFT);
            data.setVerticalAlignment(VerticalAlignment.CENTER);
            data.setBorderBottom(BorderStyle.THIN);
            data.setBorderTop(BorderStyle.THIN);
            data.setBorderRight(BorderStyle.THIN);
            data.setBorderLeft(BorderStyle.THIN);
            data.setBottomBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            data.setTopBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            data.setRightBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            data.setLeftBorderColor(IndexedColors.GREY_80_PERCENT.getIndex());
            data.setDataFormat(helper.createDataFormat().getFormat("dd/mm/yyyy"));
            vDatacriacao.setCellStyle(data);

            //verificar o porque de não estar mesclando as celulas do header até a coluna "k"

            //Criar tabela principal com exercicios
            //Criando estilos
            //titulos --> mesmo padrão do header
            CellStyle titlesTableTwo = wb.createCellStyle();
            titlesTableTwo.setAlignment(HorizontalAlignment.CENTER);
            titlesTableTwo.setVerticalAlignment(VerticalAlignment.CENTER);
            titlesTableTwo.setFillForegroundColor(azulEscuro);
            titlesTableTwo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titlesTableTwo.setBorderBottom(BorderStyle.THIN);
            titlesTableTwo.setBorderTop(BorderStyle.THIN);
            titlesTableTwo.setBorderRight(BorderStyle.THIN);
            titlesTableTwo.setBorderLeft(BorderStyle.THIN);
            titlesTableTwo.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            titlesTableTwo.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            titlesTableTwo.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            titlesTableTwo.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            //estilizando a font
            Font fontTitleTableTwo = wb.createFont();
            fontTitleTableTwo.setFontName("Arial");
            fontTitleTableTwo.setColor(IndexedColors.WHITE.getIndex());
            fontTitleTableTwo.setBold(true);
            //setando a font no estilo
            titlesTableTwo.setFont(fontTitleTableTwo);

            //valores - linha par - fundo branco
            CellStyle valuesTableTwoEven = wb.createCellStyle();
            valuesTableTwoEven.setAlignment(HorizontalAlignment.CENTER);
            valuesTableTwoEven.setVerticalAlignment(VerticalAlignment.CENTER);
            valuesTableTwoEven.setBorderBottom(BorderStyle.THIN);
            valuesTableTwoEven.setBorderTop(BorderStyle.THIN);
            valuesTableTwoEven.setBorderRight(BorderStyle.THIN);
            valuesTableTwoEven.setBorderLeft(BorderStyle.THIN);
            valuesTableTwoEven.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            valuesTableTwoEven.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            valuesTableTwoEven.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            valuesTableTwoEven.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            valuesTableTwoEven.setWrapText(true);
            valuesTableTwoEven.setFont(fontValues);
            //valores - linha impar - fundo azul acinzentado
            CellStyle valuesTableTwoOdd = wb.createCellStyle();
            valuesTableTwoOdd.setAlignment(HorizontalAlignment.CENTER);
            valuesTableTwoOdd.setVerticalAlignment(VerticalAlignment.CENTER);
            valuesTableTwoOdd.setFillForegroundColor(azulGelo);
            valuesTableTwoOdd.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            valuesTableTwoOdd.setBorderBottom(BorderStyle.THIN);
            valuesTableTwoOdd.setBorderTop(BorderStyle.THIN);
            valuesTableTwoOdd.setBorderRight(BorderStyle.THIN);
            valuesTableTwoOdd.setBorderLeft(BorderStyle.THIN);
            valuesTableTwoOdd.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            valuesTableTwoOdd.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            valuesTableTwoOdd.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            valuesTableTwoOdd.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
            valuesTableTwoOdd.setWrapText(true);
            valuesTableTwoOdd.setFont(fontValues);

            //Criando linha dos titulos da tabela 2
            Row titlesTableTwoRow = sheet.createRow(8);
            //Ela vai conter os titulos:
            //Exercicio - nome do exercicio
            Cell tExercicio = titlesTableTwoRow.createCell(0);
            tExercicio.setCellStyle(titlesTableTwo);
            tExercicio.setCellValue("Exercício");
            //Series
            Cell tSeries = titlesTableTwoRow.createCell(1);
            tSeries.setCellStyle(titlesTableTwo);
            tSeries.setCellValue("Séries");
            //Repetições
            Cell tRepeticoes = titlesTableTwoRow.createCell(2);
            tRepeticoes.setCellStyle(titlesTableTwo);
            tRepeticoes.setCellValue("Repetições");
            //Carga
            Cell tCarga = titlesTableTwoRow.createCell(3);
            tCarga.setCellStyle(titlesTableTwo);
            tCarga.setCellValue("Carga");
            //Descanso (em segundos)
            Cell tDescanso = titlesTableTwoRow.createCell(4);
            tDescanso.setCellStyle(titlesTableTwo);
            tDescanso.setCellValue("Descanso (em segundos)");
            //Potencia
//            Cell tPotencia = titlesTableTwoRow.createCell(5);
//            tPotencia.setCellStyle(titlesTableTwo);
//            tPotencia.setCellValue("Potência");
            //Intensidade
            Cell tIntensidade = titlesTableTwoRow.createCell(5);
            tIntensidade.setCellStyle(titlesTableTwo);
            tIntensidade.setCellValue("Intensidade");
            //Concluido (com opção de alteração)
            Cell tConcluido = titlesTableTwoRow.createCell(6);
            tConcluido.setCellStyle(titlesTableTwo);
            tConcluido.setCellValue("Concluído");
            //link do video
            Cell tLink = titlesTableTwoRow.createCell(7);
            tLink.setCellStyle(titlesTableTwo);
            tLink.setCellValue("Link do vídeo");
            //observações
            Cell tObservacoes = titlesTableTwoRow.createCell(8);
            tObservacoes.setCellStyle(titlesTableTwo);
            tObservacoes.setCellValue("Observações");

            //resultados -> criar linhas com base na lista -> uma linha por exercicioTreino do treino
            //10 celulas na linha
            //quantidade de linhas é o tamanho da lista
            //criar um for de 9 até o tamanho da lista + 9 e criar a linha e as celulas dela
            int exercicios = 0;
            int linhaInicial = 9;
            for(int i = 0; i < response.treino().getExercicios().size(); i++) {
                int index = linhaInicial+i;
                Row newRow = sheet.createRow(index);
                //nome do exercicio
                Cell exercicio = newRow.createCell(0);
                exercicio.setCellValue(response.treino().getExercicios().get(exercicios).getExercicio().getNome());
                if (index % 2 == 0) {
                    exercicio.setCellStyle(valuesTableTwoEven);
                } else {
                    exercicio.setCellStyle(valuesTableTwoOdd);
                }


                //series
                Cell series = newRow.createCell(1);
                series.setCellValue(response.treino().getExercicios().get(exercicios).getSeries());
                if (index % 2 == 0) {
                    series.setCellStyle(valuesTableTwoEven);
                } else {
                    series.setCellStyle(valuesTableTwoOdd);
                }

                //Repeticoes
                Cell repeticoes = newRow.createCell(2);
                repeticoes.setCellValue(response.treino().getExercicios().get(exercicios).getRepeticoes());
                if (index % 2 == 0) {
                    repeticoes.setCellStyle(valuesTableTwoEven);
                } else {
                    repeticoes.setCellStyle(valuesTableTwoOdd);
                }

                //Carga
                Cell carga = newRow.createCell(3);
                carga.setCellValue(response.treino().getExercicios().get(exercicios).getCarga());
                if (index % 2 == 0) {
                    carga.setCellStyle(valuesTableTwoEven);
                } else {
                    carga.setCellStyle(valuesTableTwoOdd);
                }

                //Descanso (em segundos)
                Cell descanso = newRow.createCell(4);
                if (response.treino().getExercicios().get(exercicios).getTempoDescanso() != null) {
                    descanso.setCellValue(response.treino().getExercicios().get(exercicios).getTempoDescanso());
                } else {
                    descanso.setCellValue("");
                }

                if (index % 2 == 0) {
                    descanso.setCellStyle(valuesTableTwoEven);
                } else {
                    descanso.setCellStyle(valuesTableTwoOdd);
                }

//                //Potencia
//                Cell potencia = newRow.createCell(5);
//                if (response.treino().getExercicios().get(exercicios).getPotencia() != null) {
//                    potencia.setCellValue(response.treino().getExercicios().get(exercicios).getPotencia());
//                } else {
//                    potencia.setCellValue("");
//                }
//
//                if (index % 2 == 0) {
//                    potencia.setCellStyle(valuesTableTwoEven);
//                } else {
//                    potencia.setCellStyle(valuesTableTwoOdd);
//                }

                //Intensidade
                Cell intensidade = newRow.createCell(5);
                if (response.treino().getExercicios().get(exercicios).getIntensidade() != null) {
                    intensidade.setCellValue(response.treino().getExercicios().get(exercicios).getIntensidade());
                } else {
                    intensidade.setCellValue("");
                }

                if (index % 2 == 0) {
                    intensidade.setCellStyle(valuesTableTwoEven);
                } else {
                    intensidade.setCellStyle(valuesTableTwoOdd);
                }

                //Concluido
                Cell concluido = newRow.createCell(6);
                concluido.setCellValue("Não");
                DataValidationHelper validationHelper = sheet.getDataValidationHelper();
                String[] opcoes = new String[]{"Sim", "Não"};
                DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(opcoes);
                CellRangeAddressList intervalo = new CellRangeAddressList(index, index, 6,6);
                DataValidation validacao = validationHelper.createValidation(constraint, intervalo);
                sheet.addValidationData(validacao);

                if (index % 2 == 0) {
                    concluido.setCellStyle(valuesTableTwoEven);
                } else {
                    concluido.setCellStyle(valuesTableTwoOdd);
                }

                //Link do video
                Cell link = newRow.createCell(7);
                link.setCellValue(response.treino().getExercicios().get(exercicios).getExercicio().getLinkVideo());
                if (index % 2 == 0) {
                    link.setCellStyle(valuesTableTwoEven);
                } else {
                    link.setCellStyle(valuesTableTwoOdd);
                }

                //Observacoes
                Cell observacoes = newRow.createCell(8);
                if (response.treino().getExercicios().get(exercicios).getObservacao() != null) {
                    observacoes.setCellValue(response.treino().getExercicios().get(exercicios).getObservacao());
                } else {
                    observacoes.setCellValue("");
                }
                if (index % 2 == 0) {
                    observacoes.setCellStyle(valuesTableTwoEven);
                } else {
                    observacoes.setCellStyle(valuesTableTwoOdd);
                }

                exercicios++;
            }

            int[] colunas = {0, 1, 2, 3, 4, 5, 6, 7, 8};
            int padding = 3 * 256;
            int larguraMinima = 10 * 256;
            int larguraMaxima = 50 * 256;

            for (int col : colunas) {
                sheet.autoSizeColumn(col);
                int largura = sheet.getColumnWidth(col) + padding;
                largura = Math.max(larguraMinima, Math.min(largura, larguraMaxima));
                sheet.setColumnWidth(col, largura);
            }


            wb.write(buffer);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=planilha-treino.xlsx")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(buffer.toByteArray());
        }
    }
}
