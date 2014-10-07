<#assign modelClassName=classDefinition.className>
   <h3>${modelClassName} Details:</h3>
    <table>

        <input type="text" class="${modelClassName?uncap_first}-id" value="<%= ${modelClassName?uncap_first} ? ${modelClassName?uncap_first}.get('id') : '' %>" hidden />
        	    
		<#list classDefinition.columns as columnData>
		<tr>
		<td>${columnData.fieldIdentifier?upper_case}</td>
		<td>:</td>
        <td>
	       	<input type="text" class="${modelClassName?uncap_first}-${columnData.fieldIdentifier}" value="<%= ${modelClassName?uncap_first} ? ${modelClassName?uncap_first}.get('${columnData.fieldIdentifier}') : '' %>"  />
    	</td>
        </tr>
		</#list>
		
		<#list classDefinition.dtoDefinition.dtoColumns as columnData>
		<tr>
		<td>${columnData.fieldIdentifier?upper_case}</td>
		<td>:</td>
        <td>
        	<input type="text" class="${modelClassName?uncap_first}-${columnData.fieldIdentifier}" value="<%= ${modelClassName?uncap_first} ? ${modelClassName?uncap_first}.get('${columnData.fieldIdentifier}') : '' %>"  />
        </td>
        </tr>
		</#list>
    
    </table>

    <input type="button" value="Save" id="<%= ${modelClassName?uncap_first} ? 'update${modelClassName}' : 'create${modelClassName}' %>"/>
    <input type="button" value="Cancel" id="closeDetails"/>

    
