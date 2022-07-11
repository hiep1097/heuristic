//package com.test;
//
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class ExcelUtils {
//    public static void fillAvgAndStdToExcel(int N, int max_iteration, int times, double avg[], double std[], int startColumn) throws IOException {
//        String excelFilePath = "C:\\Users\\HOANG\\Desktop\\GWO\\tonghop.xlsx";
//        File file = new File(excelFilePath);
//        FileInputStream inputStream = new FileInputStream(file);
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheet("Sheet1");
//
//        Cell cell_N = sheet.getRow(2).getCell(1);
//        Cell cell_Iter = sheet.getRow(3).getCell(1);
//        Cell cell_Times = sheet.getRow(4).getCell(1);
//        cell_N.setCellValue("N = "+N);
//        cell_Iter.setCellValue("Max iteration = "+ max_iteration);
//        cell_Times.setCellValue(times+" times");
//
//
//        int rowIndex = 9;
//        for (int i=0;i<23;i++){
//            Row row = sheet.getRow(rowIndex+i);
//            Cell avg_cell = row.getCell(1+startColumn);
//            Cell std_cell = row.getCell(2+startColumn);
//            avg_cell.setCellValue(avg[i]);
//            std_cell.setCellValue(std[i]);
//        }
//        inputStream.close();
//        FileOutputStream out = new FileOutputStream(file);
//        workbook.write(out);
//        out.close();
//        System.out.println("Write to excel done!");
//    }
//
//    public static void fillBestScoreToExcel(double [][] bestScore, int numOfF, int times, int startRow) throws IOException {
//        String excelFilePath = "C:\\Users\\HOANG\\Desktop\\GWO\\tonghop.xlsx";
//        File file = new File(excelFilePath);
//        FileInputStream inputStream = new FileInputStream(file);
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheet("Sheet2");
//
//        int rowIndex = 5+startRow;
//
//        for (int i=0; i<numOfF; i++){
//            Row row = sheet.getRow(rowIndex+i);
//            for (int j=0; j<times; j++){
//                Cell cell = row.getCell(j+2);
//                cell.setCellValue(bestScore[i][j]);
//            }
//        }
//
//        inputStream.close();
//        FileOutputStream out = new FileOutputStream(file);
//        workbook.write(out);
//        out.close();
//        System.out.println("Write to excel done!");
//    }
//
//    public static void fillPValueToExcel(double [] pvalue_DAGWO_DA, double [] pvalue_DAGWO_GWO, double [] pvalue_GWO_DA, int numOfF) throws IOException {
//        String excelFilePath = "C:\\Users\\HOANG\\Desktop\\GWO\\tonghop.xlsx";
//        File file = new File(excelFilePath);
//        FileInputStream inputStream = new FileInputStream(file);
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheet("Sheet1");
//
//        int rowIndex = 38;
//
//        for (int i=0; i<numOfF; i++){
//            Row row = sheet.getRow(rowIndex+i);
//            //DA_GWO & DA
//            Cell cell1 = row.getCell(1);
//            cell1.setCellValue(pvalue_DAGWO_DA[i]);
//            //DA_GWO & GWO
//            Cell cell2 = row.getCell(4);
//            cell2.setCellValue(pvalue_DAGWO_GWO[i]);
//            //GWO & DA
//            Cell cell3 = row.getCell(7);
//            cell3.setCellValue(pvalue_GWO_DA[i]);
//        }
//
//        inputStream.close();
//        FileOutputStream out = new FileOutputStream(file);
//        workbook.write(out);
//        out.close();
//        System.out.println("Write to excel done!");
//    }
//
//    public static void fillForDrawFunctionToExcel(double [] Fmin, double [] Favg, double [] X_1, double [] X_2,
//                                                  int startRowFmin, int startRowFavg, int startRowX1, int startRowX2,
//                                                  int orderOfF, int maxIter) throws IOException {
//        String excelFilePath = "C:\\Users\\HOANG\\Desktop\\GWO\\tonghop.xlsx";
//        File file = new File(excelFilePath);
//        FileInputStream inputStream = new FileInputStream(file);
//        Workbook workbook = new XSSFWorkbook(inputStream);
//        Sheet sheet = workbook.getSheet("Sheet3");
//
//        //F min
//        int rowIndex = startRowFmin+orderOfF;
//        Row row = sheet.getRow(rowIndex);
//        for (int i=0; i<maxIter; i++){
//            Cell cell = row.getCell(2+i);
//            cell.setCellValue(Fmin[i]);
//        }
//
//        //F avg
//        rowIndex = startRowFavg+orderOfF;
//        row = sheet.getRow(rowIndex);
//        for (int i=0; i<maxIter; i++){
//            Cell cell = row.getCell(2+i);
//            cell.setCellValue(Favg[i]);
//        }
//
//        //X1
//        rowIndex = startRowX1+orderOfF;
//        row = sheet.getRow(rowIndex);
//        for (int i=0; i<maxIter; i++){
//            Cell cell = row.getCell(2+i);
//            cell.setCellValue(X_1[i]);
//        }
//
//        //X2
//        rowIndex = startRowX2+orderOfF;
//        row = sheet.getRow(rowIndex);
//        for (int i=0; i<maxIter; i++){
//            Cell cell = row.getCell(2+i);
//            cell.setCellValue(X_2[i]);
//        }
//
//        inputStream.close();
//        FileOutputStream out = new FileOutputStream(file);
//        workbook.write(out);
//        out.close();
//        System.out.println("Write to excel done!");
//    }
//}
