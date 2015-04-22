package ${basePackage}.service;

import java.util.List;
import ${basePackage}.model.${classDefinition.className};
import ${basePackage}.dto.${classDefinition.className}DTO;
//import ${basePackage}.search.${classDefinition.className}SearchCriteria;

public interface ${classDefinition.className}Service {

	public String add${className}(${className} ${variableName});
	public String update${className}(${className} ${variableName});
	public String delete${className}(int id);
	public ${className} get${className}ById(int id);
	public List<${className}> getAll${classNamePlural}();
//	public List<${className}> get${classNamePlural}ByCriteria(${className}SearchCriteria criterion);
	public ${className}DTO get${className}DTOById(int id);
	public List<${className}DTO> getAll${className}DTOs();
}