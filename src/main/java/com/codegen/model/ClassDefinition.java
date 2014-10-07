package com.codegen.model;

import java.util.List;

public class ClassDefinition {
	protected String className;
	protected String tableName;
	protected List<ColumnDefinition> columns;
	protected DTODefinition dtoDefinition;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<ColumnDefinition> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}
	public DTODefinition getDtoDefinition() {
		return dtoDefinition;
	}
	public void setDtoDefinition(DTODefinition dtoDefinition) {
		this.dtoDefinition = dtoDefinition;
	}
	public ClassDefinition(String className, String tableName,
			List<ColumnDefinition> columns, DTODefinition dtoDefinition) {
		super();
		this.className = className;
		this.tableName = tableName;
		this.columns = columns;
		this.dtoDefinition = dtoDefinition;
	}
	
	@Override
	public String toString() {
		return "ClassDefinition [className=" + className + ", tableName="
				+ tableName + ", columns=" + columns + ", dtoDefinition="
				+ dtoDefinition + "]";
	}
	
	
}
