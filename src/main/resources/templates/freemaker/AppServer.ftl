package ${basePackage}.server;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;
import org.eclipse.jetty.webapp.WebAppContext;

import ${basePackage}.util.${userPreferences.appNameAcronym}Configuration;

public abstract class ${userPreferences.appNameAcronym}AppServer {

	
	public static void main(String[] args) throws Exception {
		
		Log.setLog(new Slf4jLog());
		
//		Log.info("Log is working!");
		
		int portNumber = Integer.parseInt(${userPreferences.appNameAcronym}Configuration.getAppProperty("Port"));

		Server server = new Server(portNumber);

	    String currentClassPath = ${userPreferences.appNameAcronym}AppServer.class.getClassLoader().getResource(".").toString();
	    String webappPath = currentClassPath + "../../src/main/webapp";
	    WebAppContext webAppContext = new WebAppContext(webappPath, "");
	    webAppContext.setDescriptor(webAppContext + "/WEB-INF/web.xml");
	    
	    ResourceHandler resource_handler = new ResourceHandler();
	    resource_handler.setDirectoriesListed(true);
	    resource_handler.setWelcomeFiles(new String[]{ "index.html" });
	    resource_handler.setResourceBase("src/main/webapp/");

	    HandlerList handlers = new HandlerList();
	    handlers.setHandlers(new Handler[] { 
	    		resource_handler, 
	    		//new DefaultHandler(), 
	    		webAppContext });

	    server.setHandler(handlers);
	    
	    server.start();
	    server.join();
	    
		String[] patterns = new String[] {"yyyy-MM-dd", "dd-MM-yyyy"};
		DateTimeConverter converter = new DateConverter();
		converter.setPatterns(patterns);
		ConvertUtils.register(converter, Date.class);


      }
}
