<#list classDefinitions as classDefinition>
{
    "${classDefinition.className}": [
        {
        	<#list classDefinition.columns as columnData>
            "${columnData.fieldIdentifier}": ""<#if columnData_has_next>,</#if>
            </#list>
        }
    ]<#if classDefinition_has_next>,</#if>
}
</#list>