package ${basePackage}.service;

import java.util.List;
import ${basePackage}.model.${classDefinition.className};
import ${basePackage}.dto.${classDefinition.className}DTO;

public interface ${classDefinition.className}Service {

	public int add${className}(${className} ${variableName});
	public int update${className}(${className} ${variableName});
	public int delete${className}(int id);
	public ${className} get${className}ById(int id);
	public List<${className}> getAll${classNamePlural}();
	public List<${className}> get${classNamePlural}ByCriteria(Object criterion);
	public ${className}DTO get${className}DTOById(int id);
	public List<${className}DTO> getAll${className}DTOs();
}