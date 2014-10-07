package ${basePackage}.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import ${basePackage}.model.${classDefinition.className};

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
<#if classDefinition.dtoDefinition.dtoColumns?size!=0>
@NoArgsConstructor
</#if>

public class ${classDefinition.className}DTO extends ${classDefinition.className} implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
				

	<#list classDefinition.dtoDefinition.dtoColumns as column>
	protected ${column.fieldType} ${column.fieldIdentifier};
	
	</#list>
	
	@Override
	public void loadFromResultSet(ResultSet resultSet) throws SQLException {
		super.loadFromResultSet(resultSet);
		<#list classDefinition.dtoDefinition.dtoColumns as column>
		this.${column.fieldIdentifier} = (${column.fieldType}) resultSet.getObject("${column.fieldIdentifier}");
		</#list>
	}	
}
