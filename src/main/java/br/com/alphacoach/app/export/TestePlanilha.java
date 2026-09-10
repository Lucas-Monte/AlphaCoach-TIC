package br.com.alphacoach.app.export;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestePlanilha {
    public static void main(String[] args) {
        //usar HttpServletRequest e HttpServletResponse para enviar o OutputStream para download
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Teste de planilha");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Teste para ver como vai sair");

        try (OutputStream fileOut = new FileOutputStream("output/teste.xlsx")){

            wb.write(fileOut);
            System.out.println("Passou por aqui");
            wb.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }






}
