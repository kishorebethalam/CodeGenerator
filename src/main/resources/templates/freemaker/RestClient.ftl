package ${basePackage}.restclient;

import java.util.List;

import javax.ws.rs.HttpMethod;

import ${basePackage}.model.${className};
import ${basePackage}.dto.${className}DTO;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;


public class ${className}RESTClient  extends BaseRESTClient {
	
	public ${className}RESTClient(String baseUrl) {
		super(baseUrl);
	}

	public String add${className}(${className} ${variableName}) {

		ClientResponse response = this.processRequest("${variableName}/add", HttpMethod.POST, ${variableName});
		String id = response.getEntity(String.class);
		return id;
	}
	
	public String update${className}(${className} ${variableName}) {

		@SuppressWarnings("unused")
		ClientResponse response = this.processRequest("${variableName}/update", HttpMethod.PUT, ${variableName});
		String id = response.getEntity(String.class);
		return id;
	}
	
	public String delete${className}(${className} ${variableName}) {

		@SuppressWarnings("unused")
		ClientResponse response = this.processRequest("${variableName}/" + ${variableName}.getId(), HttpMethod.DELETE, null);
		String id = response.getEntity(String.class);
		return id;
	}
	
	public ${className} get${className}(int id) {

		ClientResponse response = this.processRequest("${variableName}/" + id , HttpMethod.GET, null);
		${className} ${variableName} = response.getEntity(${className}.class);
		return ${variableName};
	}
	
	public List<${className}> getAll${className}s() {

		ClientResponse response = this.processRequest("${variableName}/all", HttpMethod.GET, null);
		return response.getEntity(new GenericType<List<${className}>>() { });
		 
	}
	
	public ${className}DTO get${className}DTO(int id) {

		ClientResponse response = this.processRequest("${variableName}/dto/" + id , HttpMethod.GET, null);
		${className}DTO ${variableName}DTO = response.getEntity(${className}DTO.class);
		return ${variableName}DTO;
	}
	
	public List<${className}DTO> getAll${className}DTOs() {

		ClientResponse response = this.processRequest("${variableName}/dto/all", HttpMethod.GET, null);
		return response.getEntity(new GenericType<List<${className}DTO>>() { });
		 
	}

	
	
}