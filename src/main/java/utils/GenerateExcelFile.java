package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import javafx.collections.ObservableList;
import manager.StartApplication;
import model.ServiceProcedureModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class GenerateExcelFile {
    
    private static String month = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, StartApplication.REGION);
    private static HSSFWorkbook workbook;
    
    public static void createFile(File file, ObservableList<ServiceProcedureModel> procedures) {
        workbook = new HSSFWorkbook();
        HSSFSheet sheetClients = workbook.createSheet("Procedimentos");
        sheetClients.setDefaultColumnWidth(20);
        sheetClients.setDefaultRowHeight((short) 600);
        int rownum = 2;
        HSSFRow rowTittles = sheetClients.createRow(0);
        HSSFCell cellTittles = rowTittles.createCell(0);
        cellTittles.setCellStyle(styleHeaderBottom(14));
        cellTittles.setCellValue("Relatórios de Procedimentos - " + month);
        HSSFRow rowSubTittles = sheetClients.createRow(1);
        String[] subTittles = {"Data", "Cliente", "Procedimento", "Valor Total", "Valor Recebido"};
        for (int i = 0; i <= 4; i++) {
            HSSFCell cell = rowSubTittles.createCell(i);
            cell.setCellStyle(styleHeaderBottom(12));
            cell.setCellValue(subTittles[i]);
        }
        for (ServiceProcedureModel procedure : procedures) {
            int cellnum = 0;
            HSSFRow row = sheetClients.createRow(rownum++);
            HSSFCell cellDate = row.createCell(cellnum++);
            cellDate.setCellStyle(styleBody());
            cellDate.setCellValue(new SimpleDateFormat("dd/MM/yyyy")
                    .format(procedure.getPerformedIn()));
            HSSFCell cellClient = row.createCell(cellnum++);
            cellClient.setCellStyle(styleBody());
            cellClient.setCellValue(procedure.getClient());
            HSSFCell cellProcedure = row.createCell(cellnum++);
            cellProcedure.setCellStyle(styleBody());
            cellProcedure.setCellValue(procedure.getType().getName());
            HSSFCell cellPrice = row.createCell(cellnum++);
            cellPrice.setCellStyle(styleBody());
            cellPrice.setCellValue(NumberFormat.getCurrencyInstance()
                .format(procedure.getPrice()));
            HSSFCell cellReceived = row.createCell(cellnum++);
            cellReceived.setCellStyle(styleBody());
            cellReceived.setCellValue(NumberFormat.getCurrencyInstance()
                    .format(procedure.getReceived()));
        }
        HSSFRow rowTotalReceived = sheetClients.createRow(procedures.size() + 2);
        HSSFCell cellTotal = rowTotalReceived.createCell(3);
        cellTotal.setCellStyle(styleBody());
        cellTotal.setCellValue("Total:");
        cellTotal.setCellStyle(styleHeaderBottom(12));
        HSSFCell cellTotalReceived = rowTotalReceived.createCell(4);
        cellTotalReceived.setCellStyle(styleHeaderBottom(12));
        cellTotalReceived.setCellValue(NumberFormat.getCurrencyInstance()
                .format(procedures.stream().mapToDouble(sp -> sp.getReceived()).sum()));
        sheetClients.addMergedRegion(new CellRangeAddress(0,0,0,4));
        try {
            FileOutputStream out = new FileOutputStream(new File(file.getAbsolutePath()));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static CellStyle styleHeaderBottom(int fontSize) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) fontSize);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private static CellStyle styleBody() {
        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setAlignment(HorizontalAlignment.CENTER);
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyStyle.setBorderBottom(BorderStyle.MEDIUM);
        bodyStyle.setBorderLeft(BorderStyle.MEDIUM);
        bodyStyle.setBorderRight(BorderStyle.MEDIUM);
        bodyStyle.setBorderTop(BorderStyle.MEDIUM);
        return bodyStyle;
    }
    
    public static String getMonth() {
        return month;
    }
    
}
