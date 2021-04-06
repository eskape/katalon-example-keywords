package sample

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword
import java.sql.Connection
import com.kms.katalon.core.util.KeywordUtil

public class SqlManager {

	private static final String CONNECTIONSTRING = "jdbc:sqlserver://%s;database=%s;user=katalon;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

	private Connection connection = null;
	private String databaseName;
	private boolean reusable;

	public SqlManager(String databaseName, boolean reusable){
		this.databaseName = databaseName
		this.reusable = reusable
	}

	@Keyword
	private def connect() {
		if (connection != null && !connection.isClosed()){
			if (reusable) {
				return connection;
			} else {
				connection.close()
			}
		}
		String endpoint = System.getenv("KATALON-SQL-ENDPOINT")
		String password = System.getenv("KATALON-SQL-PASSWORD")
		connection = DriverManager.getConnection(String.format(CONNECTIONSTRING, endpoint, databaseName, password))
		return connection
	}

	@Keyword
	def executeNonQuery(String query) {
		try {
			connect()
			Statement stm = connection.createStatement()
			return stm.execute(query)
		} catch (Exception e) {
			e.printStackTrace()
		} finally {
			if(!reusable) {
				closeDatabaseConnection()
			}
		}
	}

	@Keyword
	def execute(String query) {
		try {
			connect()
			Statement stm = connection.createStatement()
			return stm.executeQuery(query)
		} catch (Exception e) {
			e.printStackTrace()
		} finally {
			if(!reusable) {
				closeDatabaseConnection()
			}
		}
	}


	@Keyword
	private def closeDatabaseConnection() {
		if(connection != null && !connection.isClosed()){
			connection.close()
		}
		connection = null
	}

	@Keyword
	def validateConnection() {
		try {
			connect()
		} catch (Exception e) {
			e.printStackTrace()
		} finally {
			if(!reusable) {
				closeDatabaseConnection()
			}
		}
	}
}
