package com.codegen.model;

public class DTOColumnDefinition {
	protected String fieldIdentifier;
	protected String fieldType;
	protected String dbReferenceTable;
	protected String dbReferenceField;
	protected String dbReferenceTitle;
	public String getFieldIdentifier() {
		return fieldIdentifier;
	}
	public void setFieldIdentifier(String fieldIdentifier) {
		this.fieldIdentifier = fieldIdentifier;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getDbReferenceTable() {
		return dbReferenceTable;
	}
	public void setDbReferenceTable(String dbReferenceTable) {
		this.dbReferenceTable = dbReferenceTable;
	}
	public String getDbReferenceField() {
		return dbReferenceField;
	}
	public void setDbReferenceField(String dbReferenceField) {
		this.dbReferenceField = dbReferenceField;
	}
	public String getDbReferenceTitle() {
		return dbReferenceTitle;
	}
	public void setDbReferenceTitle(String dbReferenceTitle) {
		this.dbReferenceTitle = dbReferenceTitle;
	}
	public DTOColumnDefinition(String fieldIdentifier, String fieldType,
			String dbReferenceTable, String dbReferenceField,
			String dbReferenceTitle) {
		super();
		this.fieldIdentifier = fieldIdentifier;
		this.fieldType = fieldType;
		this.dbReferenceTable = dbReferenceTable;
		this.dbReferenceField = dbReferenceField;
		this.dbReferenceTitle = dbReferenceTitle;
	}
	@Override
	public String toString() {
		return "DTOColumnDefinition [fieldIdentifier=" + fieldIdentifier
				+ ", fieldType=" + fieldType + ", dbReferenceTable="
				+ dbReferenceTable + ", dbReferenceField=" + dbReferenceField
				+ ", dbReferenceTitle=" + dbReferenceTitle + "]";
	}


	
	
}
