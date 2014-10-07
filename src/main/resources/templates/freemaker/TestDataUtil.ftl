package ${basePackage};

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import ${basePackage}.model.${userPreferences.appNameAcronym}Model;
import ${basePackage}.model.${userPreferences.appNameAcronym}ModelFactory;
import ${basePackage}.util.${userPreferences.appNameAcronym}Configuration;

public class TestDataUtil {

	public static void main (String[] args) throws ClassNotFoundException, Exception{
		loadTestData();
	}
	
	private static Map<String, List<${userPreferences.appNameAcronym}Model>> testData; 
	static {
		testData = new HashMap<String, List<${userPreferences.appNameAcronym}Model>>();
		try {
			loadTestData();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<${userPreferences.appNameAcronym}Model> getTestData(String className){
		return testData.get(className);
	}
	
	private static void loadTestData() throws ClassNotFoundException, Exception {

		String rulesString = readFile("test_data.json");
		JSONObject dataJSON = new JSONObject(rulesString);
		
		@SuppressWarnings("rawtypes")
		Iterator iterator = dataJSON.sortedKeys();
		while (iterator.hasNext()){
			
			String className = (String) iterator.next();
			JSONArray dataItems = dataJSON.getJSONArray(className);
			
			String fullyQualifiedClassName = "com.minimart.model." + className;
			List<${userPreferences.appNameAcronym}Model> modelObjects = new ArrayList<${userPreferences.appNameAcronym}Model>(); 
			
			for( int i=0; i < dataItems.length() ; i++) {
				JSONObject dataItem = dataItems.getJSONObject(i); 
				
				@SuppressWarnings("unchecked")
				${userPreferences.appNameAcronym}Model model = (${userPreferences.appNameAcronym}Model) ${userPreferences.appNameAcronym}ModelFactory.createFromJSON(dataItem, (Class<? extends ${userPreferences.appNameAcronym}Model>) Class.forName(fullyQualifiedClassName));
				modelObjects.add(model);
			}
			testData.put(className, modelObjects);
		}
		
//		System.out.println("No of items in testData" + testData.size());
		System.out.println(dataJSON.toString());
	}

	private static String readFile(String filename) {
		String content = null;
		String resourcesPath = ${userPreferences.appNameAcronym}Configuration.getAppProperty("RootDirectory") + "/src/test/resources";
		String filePath = resourcesPath + File.separator + filename;
		
		System.out.println("Trying to import " + filePath);
		File file = new File(filePath); // for ex foo.txt
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("Content: " + content);
		return content;
	}
}
