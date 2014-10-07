package ${basePackage}.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ${userPreferences.appNameAcronym}Model {

	
	public ${userPreferences.appNameAcronym}Model() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract void loadFromResultSet(ResultSet resultSet) throws SQLException;
	public abstract Object[] toObjectArray(boolean includeId);
	public abstract int hashCode();
	public abstract boolean equals(Object obj);

}
