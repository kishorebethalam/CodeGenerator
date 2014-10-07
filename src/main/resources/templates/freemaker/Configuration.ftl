package ${basePackage}.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class ${userPreferences.appNameAcronym}Configuration {
	
//	public static Logger LOG = LoggerFactory.getLogger(${userPreferences.appNameAcronym}Configuration.class);
	
	private static String AppPropertiesFileName = "App.properties";
	
    private static Properties appProperties = new Properties();
    static {
        try {
        	InputStream stream = new FileInputStream(new File(AppPropertiesFileName));
        	appProperties.load(stream);
        } catch (Exception exp) {
        	exp.printStackTrace();
        }
    }
    
    public static String getAppProperty(String propertyName){
    	return appProperties.getProperty(propertyName);
    }
}
