package ${basePackage}.dao;

import java.util.List;
import ${basePackage}.model.${className};
import ${basePackage}.dto.${className}DTO;

public interface ${className}DAO {

	public int add${className}(${className} ${variableName});
	public void update${className}(${className} ${variableName});
	public void delete${className}(int id);
	public ${className} get${className}ById(int id);
	public List<${className}> getAll${classNamePlural}();
	public List<${className}> get${classNamePlural}ByCriteria(Object criterion);
	public ${className}DTO get${className}DTOById(int id);
	public List<${className}DTO> getAll${className}DTOs();
}