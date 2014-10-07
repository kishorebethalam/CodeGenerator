package com.codegen.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.codegen.model.ClassDefinition;
import com.codegen.model.ColumnDefinition;
import com.codegen.model.DTOColumnDefinition;
import com.codegen.model.DTODefinition;
import com.google.common.base.Joiner;

//
public class QueryGenerator {

	private static String QUERY_PROPERTIES_FILE_NAME = "Query.properties";

	private static Properties queryProperties;

	protected UserPreferences userPreferences;
	protected List<ClassDefinition> classDefinitions;

	protected String queryFilePath;

	public UserPreferences getUserPreferences() {
		return userPreferences;
	}

	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}

	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}

	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}

	public QueryGenerator(UserPreferences userPreferences,
			List<ClassDefinition> classDefinitions) {
		super();
		this.userPreferences = userPreferences;
		this.classDefinitions = classDefinitions;
		this.queryFilePath = this.getUserPreferences()
				.getResourceFilesBaseDirectory()
				+ "/"
				+ QUERY_PROPERTIES_FILE_NAME;

	}

	public void generateQueries() {
		startGeneration();
		for (ClassDefinition classDefinition : this.classDefinitions) {

			List<String> fieldNames = new ArrayList<String>();
			fieldNames.add("ID");
			for (ColumnDefinition columnDefinition : classDefinition
					.getColumns()) {
				fieldNames.add(columnDefinition.getDbColumnIdentifier());
			}

			this.generateInsertQuery(classDefinition.getTableName(),
					fieldNames);
			this.generateUpdateQuery(classDefinition.getTableName(),
					fieldNames);
			this.generateDeleteQuery(classDefinition.getTableName(),
					fieldNames);
			this.generateGetByIdQuery(classDefinition.getTableName(),
					fieldNames);
			this.generateGetAllQuery(classDefinition.getTableName(),
					fieldNames);
			
			this.generateGetDTOByIdQuery(classDefinition);
			this.generateGetAllDTOQuery(classDefinition);
			
		}
		completeGeneration();
	}

	private void startGeneration() {
		queryProperties = new Properties();
		try {
			File file = new File(this.queryFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			queryProperties.load(new FileInputStream(file));
			// System.out.println(queryProperties.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void completeGeneration() {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(this.queryFilePath);
			queryProperties.store(fileOut, "Queries To be used in DAOs");
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String generateGetByIdQuery(String dbTableName,
			List<String> fieldNames) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("select ");
		buffer.append(Joiner.on(", ").skipNulls().join(fieldNames));
		buffer.append(" from ");
		buffer.append(dbTableName);
		buffer.append(" where id = ? ");

		// System.out.println(buffer.toString());

		queryProperties.put(dbTableName.toUpperCase() + "_GET_BY_ID",
				buffer.toString());
		return buffer.toString();
	}

	public String generateGetDTOByIdQuery(ClassDefinition classDefinition) {

		DTODefinition dtoDefinition = classDefinition.getDtoDefinition();
		if (dtoDefinition.isUseCustomQueries()) {

			String query = dtoDefinition.getGetDTOByIdQuery();
			if (query != null && query.length() > 0) {

				queryProperties.put(classDefinition.getTableName()
						.toUpperCase() + "_GET_DTO_BY_ID", query);
				return query;
			}
		} else {

			List<String> columnSet = new ArrayList<String>();
			String idFieldName = classDefinition.getTableName() + ".ID";
			columnSet.add(idFieldName);
			
			for (ColumnDefinition field : classDefinition.getColumns()) {
				String fieldName = classDefinition.getTableName() + "."
						+ field.getDbColumnIdentifier();
				columnSet.add(fieldName);
			}
			for (DTOColumnDefinition dtoField : classDefinition
					.getDtoDefinition().getDtoColumns()) {
				String fieldName = dtoField.getDbReferenceTable() + "."
						+ dtoField.getDbReferenceTitle() + " "
						+ dtoField.getFieldIdentifier();
				columnSet.add(fieldName);
			}

			List<String> tableNames = new ArrayList<String>();
			tableNames.add(classDefinition.getTableName());

			for (DTOColumnDefinition dtoField : classDefinition
					.getDtoDefinition().getDtoColumns()) {
				tableNames.add(dtoField.getDbReferenceTable());
			}

			List<String> conditions = new ArrayList<String>();
			conditions.add(classDefinition.getTableName() + ".id = ? ");
			for (DTOColumnDefinition dtoField : classDefinition
					.getDtoDefinition().getDtoColumns()) {
				String expression = dtoField.getDbReferenceField() + " = "
						+ dtoField.getDbReferenceTable() + ".id";
				conditions.add(expression);
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			buffer.append(Joiner.on(", ").skipNulls().join(columnSet));
			buffer.append(" from ");
			buffer.append(Joiner.on(", ").skipNulls().join(tableNames));
			buffer.append(" where ").append(
					Joiner.on(" AND ").skipNulls().join(conditions));

			queryProperties.put(classDefinition.getTableName().toUpperCase()
					+ "_GET_DTO_BY_ID", buffer.toString());
			return buffer.toString();
		}
		return "";
	}

	public String generateGetAllDTOQuery(ClassDefinition classDefinition) {

		DTODefinition dtoDefinition = classDefinition.getDtoDefinition();
		if (dtoDefinition.isUseCustomQueries()) {

			String query = dtoDefinition.getGetAllDTOQuery();
			if (query != null && query.length() > 0) {
				queryProperties.put(classDefinition.getTableName()
						.toUpperCase() + "_GET_DTO_ALL", query);
				return query;
			}

		} else {
			List<String> columnSet = new ArrayList<String>();
			String idFieldName = classDefinition.getTableName() + ".ID";
			columnSet.add(idFieldName);
			
			for (ColumnDefinition field : classDefinition.getColumns()) {
				String fieldName = classDefinition.getTableName() + "."
						+ field.getDbColumnIdentifier();
				columnSet.add(fieldName);
			}
			for (DTOColumnDefinition dtoField : classDefinition
					.getDtoDefinition().getDtoColumns()) {
				String fieldName = dtoField.getDbReferenceTable() + "."
						+ dtoField.getDbReferenceTitle() + " "
						+ dtoField.getFieldIdentifier();
				columnSet.add(fieldName);
			}

			List<String> tableNames = new ArrayList<String>();
			tableNames.add(classDefinition.getTableName());

			for (DTOColumnDefinition dtoField : classDefinition
					.getDtoDefinition().getDtoColumns()) {
				tableNames.add(dtoField.getDbReferenceTable());
			}

			List<String> conditions = new ArrayList<String>();
			for (DTOColumnDefinition dtoField : classDefinition
					.getDtoDefinition().getDtoColumns()) {
				String expression = dtoField.getDbReferenceField() + " = "
						+ dtoField.getDbReferenceTable() + ".id";
				conditions.add(expression);
			}

			StringBuffer buffer = new StringBuffer();
			buffer.append("select ");
			buffer.append(Joiner.on(", ").skipNulls().join(columnSet));
			buffer.append(" from ");
			buffer.append(Joiner.on(", ").skipNulls().join(tableNames));

			if (conditions.size() > 0) {
				buffer.append(" where ").append(
						Joiner.on(" AND ").skipNulls().join(conditions));
			}

			queryProperties.put(classDefinition.getTableName().toUpperCase()
					+ "_GET_DTO_ALL", buffer.toString());
			return buffer.toString();
		}
		return "";

	}

	public String generateGetAllQuery(String dbTableName,
			List<String> fieldNames) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("select ");
		buffer.append(Joiner.on(", ").skipNulls().join(fieldNames));
		buffer.append(" from ");
		buffer.append(dbTableName);

		// System.out.println(buffer.toString());

		queryProperties.put(dbTableName.toUpperCase() + "_GET_ALL",
				buffer.toString());
		return buffer.toString();
	}

	public String generateUpdateQuery(String dbTableName,
			List<String> fieldNames) {

		List<String> applicableFields = new ArrayList<String>(fieldNames);
		applicableFields.remove("ID");

		StringBuffer buffer = new StringBuffer();
		buffer.append("update ").append(dbTableName);
		buffer.append(" set ");

		List<String> expressions = new ArrayList<String>();
		for (String fieldName : applicableFields) {
			expressions.add(fieldName + " = ? ");
		}
		buffer.append(Joiner.on(" , ").skipNulls().join(expressions));
		buffer.append(" where id = ? ");

		// System.out.println(buffer.toString());

		queryProperties.put(dbTableName.toUpperCase() + "_UPDATE",
				buffer.toString());
		return buffer.toString();
	}

	public String generateInsertQuery(String dbTableName,
			List<String> fieldNames) {

		List<String> applicableFields = new ArrayList<String>(fieldNames);
		applicableFields.remove("ID");

		String[] questionsArray = String
				.format("%0" + applicableFields.size() + "d", 0)
				.replace("0", "?,").split(",");

		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into ").append(dbTableName);
		buffer.append(" ( ");
		buffer.append(Joiner.on(", ").skipNulls().join(applicableFields));
		buffer.append(" ) ");
		buffer.append(" values ( ");
		buffer.append(Joiner.on(", ").skipNulls().join(questionsArray));
		buffer.append(" ) ");

		// System.out.println(buffer.toString());

		queryProperties.put(dbTableName.toUpperCase() + "_INSERT",
				buffer.toString());
		return buffer.toString();
	}

	public String generateDeleteQuery(String dbTableName,
			List<String> fieldNames) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(dbTableName);
		buffer.append(" where id = ? ");

		// System.out.println(buffer.toString());

		queryProperties.put(dbTableName.toUpperCase() + "_DELETE",
				buffer.toString());
		return buffer.toString();
	}

}
