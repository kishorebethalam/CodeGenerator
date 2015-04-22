drop database if exists ${userPreferences.databaseName};
create database ${userPreferences.databaseName};
connect ${userPreferences.databaseName};

<#list classDefinitions as classDefinition>
create table if not exists ${classDefinition.tableName} (
	<#assign columnsData = classDefinition.columns>
	<#assign foreignKeyColumnsData = foreignKeyColumnsMap[classDefinition.className]>
	<#assign noOfForeignKeys = foreignKeyColumnsData?size>
	<#assign uniqueColumnsData = uniqueKeyDBColumnIdsMap[classDefinition.className]>
	<#assign noOfUniqueKeys = uniqueColumnsData?size>
	ID int not null primary key auto_increment,
	<#list classDefinition.columns as columnData>
	${columnData.dbColumnIdentifier} ${columnData.dbColumnType} <#if columnData.notNull>not null</#if><#if columnData_has_next>,<#else><#if noOfForeignKeys!=0>,<#else><#if noOfUniqueKeys!=0>,</#if></#if></#if>
	</#list>
	
	<#list foreignKeyColumnsData as columnData>
	foreign key (${columnData.dbColumnIdentifier}) references ${columnData.referringTable}(ID)<#if columnData_has_next>,<#else><#if noOfUniqueKeys!=0>,</#if></#if>
	</#list>
	
	<#if noOfUniqueKeys!=0>
	unique key (<#list uniqueColumnsData as columnIdentifier>${columnIdentifier}<#if columnIdentifier_has_next>,</#if></#list>)
	</#if>
);
</#list>