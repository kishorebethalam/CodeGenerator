package ${basePackage}.service.impl;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;

import java.util.List;
import ${basePackage}.model.${className};
import ${basePackage}.service.${className}Service;
import ${basePackage}.dao.${className}DAO;
import ${basePackage}.dao.impl.${className}DAOImpl;
import ${basePackage}.dto.${className}DTO;
//import ${basePackage}.search.${classDefinition.className}SearchCriteria;

@Path("${variableName}")
public class ${className}ServiceImpl implements ${className}Service {

	protected ${className}DAO ${variableName}DAO ;
	public ${className}ServiceImpl() {
		this.${variableName}DAO = new ${className}DAOImpl();
	}
	
	@Path("add")
	@POST
	@Consumes( MediaType.APPLICATION_JSON)
	public String add${className}(${className} ${variableName}){
		return "{\"id\": " +  this.${variableName}DAO.add${className}(${variableName}) + " }";
	}
	
	@Path("update")
	@PUT
	@Consumes( MediaType.APPLICATION_JSON)
	public String update${className}(${className} ${variableName}){
		this.${variableName}DAO.update${className}(${variableName});
		return "{\"id\": " +  ${variableName}.getId()+ " }";
	}
	
	@Path("{id}")
	@DELETE
	public String delete${className}(@PathParam("id") int id){
		this.${variableName}DAO.delete${className}(id);
		return "{\"id\": " +  id+ " }";
	}
	
	@Path("{id}")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public ${className} get${className}ById(@PathParam("id") int id){
		return this.${variableName}DAO.get${className}ById(id);
	}
	
	@Path("all")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public List<${className}> getAll${classNamePlural}(){
		return this.${variableName}DAO.getAll${classNamePlural}();
	}
	
	@Path("dto/{id}")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public ${className}DTO get${className}DTOById(@PathParam("id") int id){
		return this.${variableName}DAO.get${className}DTOById(id);
	}
	
	@Path("dto/all")
	@GET
	@Produces( MediaType.APPLICATION_JSON)
	public List<${className}DTO> getAll${className}DTOs(){
		return this.${variableName}DAO.getAll${className}DTOs();
	}
/*	@Path("search")
	@POST
	@Produces( MediaType.APPLICATION_JSON)
	public List<${className}> get${classNamePlural}ByCriteria(${className}SearchCriteria criterion){
		return this.${variableName}DAO.get${classNamePlural}ByCriteria(criterion);
	}
	*/
}

