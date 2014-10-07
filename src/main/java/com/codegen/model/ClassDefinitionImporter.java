package com.codegen.model;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ClassDefinitionImporter {
		
	private static int DATA_START_ROW = 5;
	private static int COLUMN_DATA_START_COLUMN = 0;
	private static int DTO_COLUMN_DATA_START_COLUMN = 9;
	
	public static void main(String[] args) 
    {
        importFromExcel("/Users/kbethalam/Documents/DBSchema.xlsx");
    }

	public static List<ClassDefinition> importFromExcel(String filePath) {
        
		List<ClassDefinition> definitions = new ArrayList<ClassDefinition>();
        
        try
        {
            FileInputStream file = new FileInputStream(new File(filePath));
 
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 

            
            int noOfSheets = workbook.getNumberOfSheets();
            for (int sheetIndex = 0; sheetIndex < noOfSheets; sheetIndex++){
            	ClassDefinition classDefinition = getClassDefinition(workbook, sheetIndex);
            	definitions.add(classDefinition);
            }

            file.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
		
		return definitions;
	}

	private static ClassDefinition getClassDefinition(XSSFWorkbook workbook, int sheetIndex) {
		XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
		
		
		String className = getClassFromSheet(sheet);

		String tableName = getTableFromSheet(sheet).toUpperCase();

		List<ColumnDefinition> columnDefinitions = getColumnDefinitions(sheet);
		DTODefinition dtoDefinition = getDTODefinition(sheet);
		
		ClassDefinition classDefinition = new ClassDefinition(className, tableName, columnDefinitions, dtoDefinition);
		return classDefinition;
	}

	private static String getClassFromSheet(XSSFSheet sheet) {
		return getValue(sheet, 0, 1);
	}
	private static String getTableFromSheet(XSSFSheet sheet) {
		return getValue(sheet, 1, 1);
	}
	

	private static int getEndRowWithData(XSSFSheet sheet, int startRow, int column){
		
		int currentRow = startRow;
		boolean isMoreDataExists = true;
		while (isMoreDataExists){

			Row classRow = sheet.getRow(currentRow);
			if (classRow != null && classRow.getCell(column) != null){
				String value = classRow.getCell(column).getStringCellValue();
				if (value == null || value.trim().length() == 0){
					isMoreDataExists = false;
				}
			}
			else {
				isMoreDataExists = false;
			}
			if (!isMoreDataExists){
				break;
			}
			currentRow++;
		}
		if (currentRow == startRow){
			return -1;
		}
		return currentRow - 1;
	}
	private static String getValue(XSSFSheet sheet, int rowNum, int columNum) {
		Row row = sheet.getRow(rowNum);
		if (row != null){
			Cell cell = row.getCell(columNum);
			if (cell != null) {
				return cell.getStringCellValue();
			}
		}
		return "";
	}
	
	private static List<ColumnDefinition> getColumnDefinitions(
	XSSFSheet sheet) {
		
		List<ColumnDefinition> columnDefinitions = new ArrayList<ColumnDefinition>();
		
		int endRow = getEndRowWithData(sheet, DATA_START_ROW, COLUMN_DATA_START_COLUMN);
		if (endRow == -1){
			return columnDefinitions;
		}
		
		for (int currentRow = DATA_START_ROW; currentRow <= endRow ; currentRow++) {
		
			Row classRow = sheet.getRow(currentRow);
			
			String fieldId = classRow.getCell(0).getStringCellValue();
			String fieldTyp = classRow.getCell(1).getStringCellValue();
			String dbIdentifier = classRow.getCell(2).getStringCellValue().toUpperCase();
			String dbType = classRow.getCell(3).getStringCellValue();
			boolean isForeignKey = classRow.getCell(4).getBooleanCellValue();
			String referenceTable = "";
			if (classRow.getCell(5) != null){
				referenceTable = classRow.getCell(5).getStringCellValue(); 
			}
			boolean isNotNull = classRow.getCell(6).getBooleanCellValue();
			boolean isUnique = classRow.getCell(7).getBooleanCellValue();

			ColumnDefinition columnDefinition = new ColumnDefinition(fieldId, fieldTyp, dbIdentifier, dbType, isForeignKey, referenceTable, isNotNull, isUnique);
			
			columnDefinitions.add(columnDefinition);
			
		}
		
		return columnDefinitions;
	}

	private static DTODefinition getDTODefinition(
		XSSFSheet sheet) {
		
		List<DTOColumnDefinition> columns = getDTOColumnDefinitions(sheet);
		String getById = getValue(sheet, 4, 15);
		String getAllQuery = getValue(sheet, 5, 15);
		
		boolean isCustomQuerySpecified = getById != null && getById.trim().length() > 0 ; 
		DTODefinition definition = new DTODefinition(columns, isCustomQuerySpecified, getById, getAllQuery);
		
		return definition;
	}
	private static List<DTOColumnDefinition> getDTOColumnDefinitions(
			XSSFSheet sheet) {

		List<DTOColumnDefinition> columnDefinitions = new ArrayList<DTOColumnDefinition>();

		
		int endRow = getEndRowWithData(sheet, DATA_START_ROW, DTO_COLUMN_DATA_START_COLUMN);

		if (endRow == -1){
			return columnDefinitions;
		}
		
		for (int currentRow = DATA_START_ROW; currentRow <= endRow; currentRow++) {
			Row classRow = sheet.getRow(currentRow);

			String fieldId = classRow.getCell(DTO_COLUMN_DATA_START_COLUMN ).getStringCellValue();
			String fieldType = classRow.getCell(DTO_COLUMN_DATA_START_COLUMN + 1).getStringCellValue();
			
			String referenceTable = "";
			if (classRow.getCell(DTO_COLUMN_DATA_START_COLUMN + 2) != null) {
				referenceTable = classRow.getCell(DTO_COLUMN_DATA_START_COLUMN + 2).getStringCellValue();
			}
			
			String referenceTitleField = "";
			if (classRow.getCell(DTO_COLUMN_DATA_START_COLUMN + 3) != null) {
				referenceTitleField = classRow.getCell(DTO_COLUMN_DATA_START_COLUMN + 3).getStringCellValue();
			}
			
			String referingField = "";
			if (classRow.getCell(DTO_COLUMN_DATA_START_COLUMN + 4) != null) {
				referingField = classRow.getCell(DTO_COLUMN_DATA_START_COLUMN + 4).getStringCellValue();
			}
			
			DTOColumnDefinition columnDefinition = new DTOColumnDefinition(fieldId, fieldType, referenceTable, referingField, referenceTitleField);
			
			columnDefinitions.add(columnDefinition);
			
		}
		
		return columnDefinitions;
	}
}
