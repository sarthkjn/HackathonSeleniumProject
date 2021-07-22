package com.courses.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileIO {
	private static FileInputStream read_file;
	private static XSSFWorkbook workbook;
	private static XSSFSheet worksheet;
	private static Row row;
	private static Properties prop;

	/************** Get properties file ****************/
	public static Properties initProperties() {
		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(
						System.getProperty("user.dir") + "/src/test/resources/objectrepository/config.properties");
				prop.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	/************** Get Test Data from excel sheet based on Test Name **************/
	public static HashMap<String, ArrayList<String>> readExcelData(String testName) throws IOException {

		// Creating hashmap to store data
		HashMap<String, ArrayList<String>> data = new HashMap<>();

		// Reading the excel sheet
		read_file = new FileInputStream(System.getProperty("user.dir") + prop.getProperty("testData_path"));
		workbook = new XSSFWorkbook(read_file);
		worksheet = workbook.getSheet(testName);

		// Iterating over all cells in the sheet
		Iterator<Row> rowIterator;
		ArrayList<String> rowData = new ArrayList<>();
		rowIterator = worksheet.iterator();
		int rowNum = 1;// "1": {..}
		if (rowIterator.hasNext())
			row = rowIterator.next();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			Iterator<Cell> cellIterator = row.iterator();
			if (row.getCell(0) == null) {
				break;
			}

			// Writing cell data to hashmap based on cell data type
			rowData = new ArrayList<>();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				CellType type = cell.getCellType();
				if (type.equals(CellType.STRING))
					rowData.add(cell.getStringCellValue());
				else if (type.equals(CellType.NUMERIC))
					rowData.add("" + (int) cell.getNumericCellValue());
			}
			data.put("" + (rowNum), rowData);
			rowNum++;
		}

		// Closing FileInputStream and XSSFWorkbook
		workbook.close();
		read_file.close();
		return data;
	}

	/************** Writing Output Data to excel sheet ****************/
	public static void writeExcel(List<String> list1, List<String> list2, String column1, String column2,
			String sheetName) throws Exception {

		String path = System.getProperty("user.dir");
		File src = new File(path + "\\OutputData\\OutputData.xlsx");
		FileInputStream fin = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fin);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		fin.close();

		sheet.createRow(0).createCell(0).setCellValue(column1);
		sheet.getRow(0).createCell(1).setCellValue(column2);

		// for storing data in excel sheet
		for (int i = 0; i < list1.size(); ++i) {
			sheet.createRow(i + 1).createCell(0).setCellValue(list1.get(i));
		}

		for (int i = 0; i < list2.size(); ++i) {
			sheet.getRow(i + 1).createCell(1).setCellValue(list2.get(i));
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);

		// for adding color in first row
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIME.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		sheet.getRow(0).getCell(0).setCellStyle(style);
		sheet.getRow(0).getCell(1).setCellStyle(style);

		FileOutputStream fout = new FileOutputStream(src);
		workbook.write(fout);
		fout.close();
		workbook.close();
	}

	/*********** Writing Output Data to excel sheet: Form Data Override ***********/
	public static void writeExcel(String data, String column, String sheetName, int row) throws Exception {

		String path = System.getProperty("user.dir");
		File src = new File(path + "\\OutputData\\OutputData.xlsx");
		FileInputStream fin = new FileInputStream(src);
		XSSFWorkbook workbook = new XSSFWorkbook(fin);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		fin.close();

		sheet.createRow(0).createCell(0).setCellValue(column);

		// for storing data in excel sheet
		sheet.createRow(row).createCell(0).setCellValue(data);
		sheet.autoSizeColumn(0);

		// for adding color in first row
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIME.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		sheet.getRow(0).getCell(0).setCellStyle(style);

		FileOutputStream fout = new FileOutputStream(src);
		workbook.write(fout);
		fout.close();
		workbook.close();
	}
}
