package com.codegen.model;

import java.util.List;

public class DTODefinition {
	
	protected List<DTOColumnDefinition> dtoColumns;
	protected boolean useCustomQueries;
	protected String getDTOByIdQuery;
	protected String getAllDTOQuery;
	
	public List<DTOColumnDefinition> getDtoColumns() {
		return dtoColumns;
	}
	public void setDtoColumns(List<DTOColumnDefinition> dtoColumns) {
		this.dtoColumns = dtoColumns;
	}
	public boolean isUseCustomQueries() {
		return useCustomQueries;
	}
	public void setUseCustomQueries(boolean useCustomQueries) {
		this.useCustomQueries = useCustomQueries;
	}
	public String getGetDTOByIdQuery() {
		return getDTOByIdQuery;
	}
	public void setGetDTOByIdQuery(String getDTOByIdQuery) {
		this.getDTOByIdQuery = getDTOByIdQuery;
	}
	public String getGetAllDTOQuery() {
		return getAllDTOQuery;
	}
	public void setGetAllDTOQuery(String getAllDTOQuery) {
		this.getAllDTOQuery = getAllDTOQuery;
	}
	
	public DTODefinition(List<DTOColumnDefinition> dtoColumns,
			boolean useCustomQueries, String getDTOByIdQuery,
			String getAllDTOQuery) {
		super();
		this.dtoColumns = dtoColumns;
		this.useCustomQueries = useCustomQueries;
		this.getDTOByIdQuery = getDTOByIdQuery;
		this.getAllDTOQuery = getAllDTOQuery;
	}
	
	@Override
	public String toString() {
		return "DTODefinition [dtoColumns=" + dtoColumns
				+ ", useCustomQueries=" + useCustomQueries
				+ ", getDTOByIdQuery=" + getDTOByIdQuery + ", getAllDTOQuery="
				+ getAllDTOQuery + "]";
	}
	
}
