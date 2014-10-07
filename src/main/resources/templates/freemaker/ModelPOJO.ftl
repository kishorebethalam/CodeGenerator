package ${basePackage}.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.*;



@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude="id", callSuper=false)
@AllArgsConstructor
@NoArgsConstructor

public class ${classDefinition.className} extends ${userPreferences.appNameAcronym}Model implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	protected Integer id;

	<#list classDefinition.columns as column>
	protected ${column.fieldType} ${column.fieldIdentifier};

	</#list>
	
	@Override
	public void loadFromResultSet(ResultSet resultSet) throws SQLException {
		this.id = resultSet.getInt("ID");
		<#list classDefinition.columns as column>
		this.${column.fieldIdentifier} = (${column.fieldType}) resultSet.getObject("${column.dbColumnIdentifier}");
		</#list>
	}	
	
	@Override
	public Object[] toObjectArray(boolean includeId) {

		if (includeId) {
			Object[] params = { 
				<#list classDefinition.columns as column> this.${column.fieldIdentifier}<#if column_has_next>,</#if> </#list>, 
				this.id 
			};
			return params;
		} else {
			Object[] params = { 
				<#list classDefinition.columns as column> this.${column.fieldIdentifier}<#if column_has_next>,</#if> </#list>
			};
			return params;
		}
	}
	
}
