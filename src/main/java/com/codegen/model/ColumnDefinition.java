package com.codegen.model;

public class ColumnDefinition {
	protected String fieldIdentifier;
	protected String fieldType;
	protected String dbColumnIdentifier;
	protected String dbColumnType;
	protected boolean foreignKey;
	protected String referringTable;
	protected boolean notNull;
	protected boolean isUnique;
	
	
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


	public String getDbColumnIdentifier() {
		return dbColumnIdentifier;
	}


	public void setDbColumnIdentifier(String dbColumnIdentifier) {
		this.dbColumnIdentifier = dbColumnIdentifier;
	}


	public String getDbColumnType() {
		return dbColumnType;
	}


	public void setDbColumnType(String dbColumnType) {
		this.dbColumnType = dbColumnType;
	}


	public boolean isForeignKey() {
		return foreignKey;
	}


	public void setForeignKey(boolean isForeignKey) {
		this.foreignKey = isForeignKey;
	}


	public String getReferringTable() {
		return referringTable;
	}


	public void setReferringTable(String referringTable) {
		this.referringTable = referringTable;
	}


	public boolean isNotNull() {
		return notNull;
	}


	public void setNotNull(boolean isNotNull) {
		this.notNull = isNotNull;
	}


	public boolean isUnique() {
		return isUnique;
	}


	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}


	public ColumnDefinition(String fieldIdentifier, String fieldType,
			String dbColumnIdentifier, String dbColumnType,
			boolean isForeignKey, String referringTable, boolean isNotNull,
			boolean isUnique) {
		super();
		this.fieldIdentifier = fieldIdentifier;
		this.fieldType = fieldType;
		this.dbColumnIdentifier = dbColumnIdentifier;
		this.dbColumnType = dbColumnType;
		this.foreignKey = isForeignKey;
		this.referringTable = referringTable;
		this.notNull = isNotNull;
		this.isUnique = isUnique;
	}


	@Override
	public String toString() {
		return "ColumnDefinition [fieldIdentifier=" + fieldIdentifier
				+ ", fieldType=" + fieldType + ", dbColumnIdentifier="
				+ dbColumnIdentifier + ", dbColumnType=" + dbColumnType
				+ ", isForeignKey=" + foreignKey + ", referringTable="
				+ referringTable + ", isNotNull=" + notNull + ", isUnique="
				+ isUnique + "]";
	}

	
}
