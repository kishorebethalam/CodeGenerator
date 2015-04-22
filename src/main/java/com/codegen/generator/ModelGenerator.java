package com.codegen.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.atteo.evo.inflector.English;

import com.codegen.model.ClassDefinition;
import com.codegen.model.ClassDefinitionImporter;
import com.codegen.model.ColumnDefinition;
import com.codegen.util.ShellComandInvoker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ModelGenerator {

	// protected static String generatedJavaFilesBaseDirectory =
	// targetAppDirectory + "/src/main/java/" + basePcakage.replace(".", "/") ;
	// protected static String generatedModelFilesFolderPath =
	// generatedJavaFilesBaseDirectory + "/" + "model";
	// protected static String generatedDTOFilesFolderPath =
	// generatedJavaFilesBaseDirectory + "/" + "dto";;
	// protected static String generatedSQLFilesFolderPath = targetAppDirectory
	// + "/src/main/resources";

	protected static String templatesFolderPath = "/Users/kbethalam/Documents/workspaces/personal/CodeGenerator/src/main/resources/templates/freemaker/";
	protected static String webappsFolderPath = "/Users/kbethalam/Documents/workspaces/personal/CodeGenerator/src/main/webapp";

	protected UserPreferences userPreferences;
	protected List<ClassDefinition> classDefinitions;

	public UserPreferences getUserPreferences() {
		return userPreferences;
	}

	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}

	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}

	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}

	public ModelGenerator(UserPreferences userPreferences,
			List<ClassDefinition> classDefinitions) {
		super();
		this.userPreferences = userPreferences;
		this.classDefinitions = classDefinitions;
	}

	public static void main(String[] args) throws IOException,
			TemplateException {

//		UserPreferences(String databaseName, String appNameAcronym, String appRootDirectory,
//				String appPacakgeName, String appWebPort, String dbUserName, String dbPassword)
		
		UserPreferences preferences = new UserPreferences("angelrewards", "AngelRewards",
				"/Users/kbethalam/Desktop/generated-sources/AngelRewards", "com.angelrewards", "8081", "root", "root");

		List<ClassDefinition> classDefinitions = ClassDefinitionImporter
				.importFromExcel("/Users/kbethalam/Downloads/DBSchema-Angel-Rewards.xlsx", -1);

		System.out.println(preferences.getAppWebPort());
		

		ModelGenerator modelGenerator = new ModelGenerator(preferences,
				classDefinitions);
		
		modelGenerator.generateSQLScript();
		modelGenerator.generateModels();
		
		modelGenerator.generateWebResources();
		
		QueryGenerator queryGenerator = new QueryGenerator(preferences, classDefinitions);
		queryGenerator.generateQueries();
		
//		ShellComandInvoker.executeCommand("cd " + preferences.getAppRootDirectory());
//		ShellComandInvoker.executeCommand("mvn eclipse:eclipse");
//		ShellComandInvoker.executeCommand("mvn clean package install -DskipTests");

	}

	public void generateWebResources() throws IOException, TemplateException{
		for (ClassDefinition classDefinition : this.getClassDefinitions()) {
			generateHTMLListTemplate(classDefinition);
			generateHTMLDetailsTemplate(classDefinition);
		}
		generateJSAppRouter();
		generateJSViewController();
		generateJSModelController();
	}
	
	public void generateModels() throws IOException, TemplateException {
		generateCommonFiles();
		for (ClassDefinition classDefinition : this.getClassDefinitions()) {
			generatePOJO(classDefinition);
			generateDTO(classDefinition);
			generateServiceInterface(classDefinition);
			generateServiceImpl(classDefinition);
			generateDAOInterface(classDefinition);
			generateDAOImpl(classDefinition);
			generateRestClient(classDefinition);
			generateServiceTest(classDefinition);
		}

	}

	private Template getTemplateByName(String templateName) throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(templatesFolderPath));
		try {
			// Load template from source folder
			Template template = cfg.getTemplate(templateName);
			return template;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, Object> getCommonDataForTemplate() {

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("basePackage", userPreferences.getAppPacakgeName());
		data.put("userPreferences", userPreferences);
		return data;
	}
	
	public void generateCommonFiles() throws IOException, TemplateException{
		copyCommonWebFiles();
		generateAppServer();
		generateBaseModel();
		generateModelFactory();
		generateBaseRestClient();
		generateDBUtil();
		generateDAOException();
		generateConfiguration();
		generateTestDataUtil();
		generateTestData();
		generatePOM();
		generateAppProperties();
	}
	private void copyCommonWebFiles() throws IOException{
		File srcDir = new File(webappsFolderPath);
		File destDir = new File(this.userPreferences.getAppRootDirectory() + "/src/main/webapp");
		FileUtils.copyDirectory(srcDir, destDir);
	}
	private void generateTestData()
			throws IOException, TemplateException {

		Template template = getTemplateByName("TestDataJSON.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinitions", this.classDefinitions);

		String folderPath = this.userPreferences.getTestResourceFilesBaseDirectory() ;

		generateFile(folderPath,  "test_data.json",
				template, data);

	}
	
	private void generatePOM()
			throws IOException, TemplateException {

		Template template = getTemplateByName("POM.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinitions", this.classDefinitions);

		String folderPath = this.userPreferences.getAppRootDirectory() ;

		generateFile(folderPath,  "pom.xml",
				template, data);

	}
	
	private void generateAppProperties()
			throws IOException, TemplateException {

		Template template = getTemplateByName("AppProperties.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getAppRootDirectory() ;

		generateFile(folderPath,  "App.properties",
				template, data);

	}
	private void generateTestDataUtil()
			throws IOException, TemplateException {

		Template template = getTemplateByName("TestDataUtil.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getJavaTestFilesBaseDirectory() ;

		generateFile(folderPath,  "TestDataUtil.java",
				template, data);

	}
	private void generateAppServer()
			throws IOException, TemplateException {

		Template template = getTemplateByName("AppServer.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/server";

		generateFile(folderPath,  userPreferences.getAppNameAcronym() + "AppServer.java",
				template, data);

	}
	private void generateBaseModel()
			throws IOException, TemplateException {

		Template template = getTemplateByName("BaseModel.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/model";

		generateFile(folderPath,  userPreferences.getAppNameAcronym() + "Model.java",
				template, data);

	}
	private void generateModelFactory()
			throws IOException, TemplateException {

		Template template = getTemplateByName("BaseModelFactory.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/model";

		generateFile(folderPath,  userPreferences.getAppNameAcronym() + "ModelFactory.java",
				template, data);

	}
	private void generateConfiguration()
			throws IOException, TemplateException {

		Template template = getTemplateByName("Configuration.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/util";

		generateFile(folderPath,  userPreferences.getAppNameAcronym() + "Configuration.java",
				template, data);

	}
	private void generateBaseRestClient()
			throws IOException, TemplateException {

		Template template = getTemplateByName("BaseRestClient.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/restclient";

		generateFile(folderPath,  "BaseRESTClient.java",
				template, data);

	}
	
	private void generateDAOException()
			throws IOException, TemplateException {

		Template template = getTemplateByName("DAOException.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/dao/util";

		generateFile(folderPath,  "DAOException.java",
				template, data);

	}
	private void generateDBUtil()
			throws IOException, TemplateException {

		Template template = getTemplateByName("DBUtil.ftl");

		Map<String, Object> data = getCommonDataForTemplate();

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/dao/util";

		generateFile(folderPath,  "DBUtil.java",
				template, data);

	}
	public void generatePOJO(ClassDefinition classDefinition)
			throws IOException, TemplateException {

		Template template = getTemplateByName("ModelPOJO.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinition", classDefinition);

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/model";

		generateFile(folderPath, classDefinition.getClassName() + ".java",
				template, data);

	}

	public void generateDTO(ClassDefinition classDefinition)
			throws IOException, TemplateException {

		Template template = getTemplateByName("DTO.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinition", classDefinition);

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/dto";

		generateFile(folderPath, classDefinition.getClassName() + "DTO.java",
				template, data);
	}

	private Map<String, Object> getCommonDataForClassTemplate(ClassDefinition classDefinition) {

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinition", classDefinition);
		data.put("className", classDefinition.getClassName());
		data.put("variableName", getVariableName(classDefinition.getClassName()));
		data.put("classNamePlural", English.plural(classDefinition.getClassName()));
		data.put("dbTableName", classDefinition.getTableName());
		
		return data;
	}

	public void generateServiceInterface(ClassDefinition classDefinition) throws IOException, TemplateException {

		Template template = getTemplateByName("ServiceInterface.ftl");

		Map<String, Object> data = getCommonDataForClassTemplate(classDefinition);

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/service";

		generateFile(folderPath, classDefinition.getClassName() + "Service.java",
				template, data);
	}
	
	private void generateServiceImpl(ClassDefinition classDefinition) throws IOException, TemplateException {

		Template template = getTemplateByName("ServiceImpl.ftl");

		Map<String, Object> data = getCommonDataForClassTemplate(classDefinition);

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/service/impl";

		generateFile(folderPath, classDefinition.getClassName() + "ServiceImpl.java",
				template, data);
		
	}

	private void generateDAOInterface(ClassDefinition classDefinition) throws IOException, TemplateException {

		Template template = getTemplateByName("DAOInterface.ftl");

		Map<String, Object> data = getCommonDataForClassTemplate(classDefinition);

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/dao";

		generateFile(folderPath, classDefinition.getClassName() + "DAO.java",
				template, data);
		
	}

	private void generateDAOImpl(ClassDefinition classDefinition) throws IOException, TemplateException {

		Template template = getTemplateByName("DAOImpl.ftl");

		Map<String, Object> data = getCommonDataForClassTemplate(classDefinition);

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/dao/impl";

		generateFile(folderPath, classDefinition.getClassName() + "DAOImpl.java",
				template, data);
		
	}
	
	private void generateServiceTest(ClassDefinition classDefinition) throws IOException, TemplateException {

		Template template = getTemplateByName("ServiceTest.ftl");

		Map<String, Object> data = getCommonDataForClassTemplate(classDefinition);

		String folderPath = this.userPreferences.getJavaTestFilesBaseDirectory() + "/service";

		generateFile(folderPath, classDefinition.getClassName() + "ServiceTest.java",
				template, data);
		
	}

	private void generateRestClient (ClassDefinition classDefinition) throws IOException, TemplateException {

		Template template = getTemplateByName("RestClient.ftl");

		Map<String, Object> data = getCommonDataForClassTemplate(classDefinition);

		String folderPath = this.userPreferences.getJavaFilesBaseDirectory() + "/restclient";

		generateFile(folderPath, classDefinition.getClassName() + "RESTClient.java",
				template, data);
		
	}
	public void generateSQLScript() throws IOException, TemplateException {

		Map<String, List<ColumnDefinition>> foreignKeyColumnsMap = new HashMap<String, List<ColumnDefinition>>();
		Map<String, List<String>> uniqueKeyDBColumnIdsMap = new HashMap<String, List<String>>();

		for (ClassDefinition classDefinition : this.classDefinitions) {
			List<ColumnDefinition> foreignKeyColumns = new ArrayList<ColumnDefinition>();
			List<String> uniqueKeyDBColumnIds = new ArrayList<String>();

			for (ColumnDefinition columnDefinition : classDefinition
					.getColumns()) {
				if (columnDefinition.isUnique()) {
					uniqueKeyDBColumnIds.add(columnDefinition
							.getDbColumnIdentifier());
				}
				if (columnDefinition.isForeignKey()) {
					foreignKeyColumns.add(columnDefinition);
				}
			}
			foreignKeyColumnsMap.put(classDefinition.getClassName(),
					foreignKeyColumns);
			uniqueKeyDBColumnIdsMap.put(classDefinition.getClassName(),
					uniqueKeyDBColumnIds);

		}

		Template template = getTemplateByName("db_script.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinitions", classDefinitions);
		data.put("uniqueKeyDBColumnIdsMap", uniqueKeyDBColumnIdsMap);
		data.put("foreignKeyColumnsMap", foreignKeyColumnsMap);

		String folderPath = this.userPreferences.getResourceFilesBaseDirectory();

		generateFile(folderPath, "init_db.sql",  template, data);
		
	}
	
	private void generateJSAppRouter() throws IOException, TemplateException {

		Template template = getTemplateByName("JSAppRouter.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinitions", classDefinitions);
		data.put("appPort", userPreferences.getAppWebPort());

		String folderPath = this.userPreferences.getWebResourceFilesBaseDirectory() + "/js";

		generateFile(folderPath, "AppRouter.js",  template, data);
	}
	
	private void generateJSViewController() throws IOException, TemplateException {

		Template template = getTemplateByName("JSViewController.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinitions", classDefinitions);
		data.put("appPort", userPreferences.getAppWebPort());

		String folderPath = this.userPreferences.getWebResourceFilesBaseDirectory() + "/js";

		generateFile(folderPath, "ViewController.js",  template, data);
	}
	
	private void generateJSModelController() throws IOException, TemplateException {

		Template template = getTemplateByName("JSModelController.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinitions", classDefinitions);
		data.put("appPort", userPreferences.getAppWebPort());
		
		System.out.println(userPreferences.getAppWebPort());

		String folderPath = this.userPreferences.getWebResourceFilesBaseDirectory() + "/js";

		generateFile(folderPath, "ModelController.js",  template, data);
	}
	
	private void generateHTMLListTemplate(ClassDefinition classDefinition) throws IOException, TemplateException {

		Template template = getTemplateByName("html-list-template.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinition", classDefinition);
		data.put("appPort", userPreferences.getAppWebPort());

		String folderPath = this.userPreferences.getWebResourceFilesBaseDirectory() + "/templates";

		generateFile(folderPath, classDefinition.getClassName().toLowerCase() + "-list-template.html",  template, data);
	}
	private void generateHTMLDetailsTemplate(ClassDefinition classDefinition) throws IOException, TemplateException {

		Template template = getTemplateByName("html-details-template.ftl");

		Map<String, Object> data = getCommonDataForTemplate();
		data.put("classDefinition", classDefinition);
		data.put("appPort", userPreferences.getAppWebPort());

		String folderPath = this.userPreferences.getWebResourceFilesBaseDirectory() + "/templates";

		generateFile(folderPath, classDefinition.getClassName().toLowerCase() + "-details-template.html",  template, data);
	}

	public static void generateFile(String parentDirectoryPath,
			String fileName, Template template, Map<String, Object> data)
			throws IOException, TemplateException {

		File parentDirectory = new File(parentDirectoryPath);

		if (!parentDirectory.exists()) {
			parentDirectory.mkdirs();
		}

		Writer fileWriter = new FileWriter(parentDirectoryPath + "/" + fileName);
		
		System.out.println("Generated :" + parentDirectoryPath + "/" + fileName);
		template.process(data, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}
	private String getVariableName(String className) {
		System.out.println("Getting variable name out of className :" + className);
		return className.substring(0, 1).toLowerCase() + className.substring(1);
	}

}
