package com.codegen.generator;

public class UserPreferences {
	protected String databaseName;
	protected String appNameAcronym;
	protected String appRootDirectory;
	protected String appPacakgeName;
	protected String appWebPort;
	protected String dbUserName;
	protected String dbPassword;
	
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public String getAppNameAcronym() {
		return appNameAcronym;
	}
	public void setAppNameAcronym(String appNameAcronym) {
		this.appNameAcronym = appNameAcronym;
	}
	public String getAppRootDirectory() {
		return appRootDirectory;
	}
	public void setAppRootDirectory(String appRootDirectory) {
		this.appRootDirectory = appRootDirectory;
	}
	
	public String getAppPacakgeName() {
		return appPacakgeName;
	}
	public void setAppPacakgeName(String appPacakgeName) {
		this.appPacakgeName = appPacakgeName;
	}
	
	public String getAppWebPort() {
		return appWebPort;
	}
	public void setAppWebPort(String appWebPort) {
		this.appWebPort = appWebPort;
	}
	
	public String getDbUserName() {
		return dbUserName;
	}
	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public UserPreferences(String databaseName, String appNameAcronym, String appRootDirectory,
			String appPacakgeName, String appWebPort, String dbUserName, String dbPassword) {
		super();
		this.databaseName = databaseName;
		this.appNameAcronym = appNameAcronym.toUpperCase();
		this.appRootDirectory = appRootDirectory;
		this.appPacakgeName = appPacakgeName;
		this.appWebPort = appWebPort;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
	}
	
	public String getJavaFilesBaseDirectory(){
		return this.appRootDirectory + "/src/main/java/" + this.appPacakgeName.replace(".", "/"); 
	}
	
	public String getResourceFilesBaseDirectory(){
		return this.appRootDirectory + "/src/main/resources" ; 
	}
	
	public String getWebResourceFilesBaseDirectory(){
		return this.appRootDirectory + "/src/main/webapp/resources" ; 
	}
	
	public String getJavaTestFilesBaseDirectory(){
		return this.appRootDirectory + "/src/test/java/" + this.appPacakgeName.replace(".", "/"); 
	}
	
	public String getTestResourceFilesBaseDirectory(){
		return this.appRootDirectory + "/src/test/resources" ; 
	}
}
