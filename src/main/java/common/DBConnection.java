package common;

import com.mchange.v2.c3p0.*;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
* Provee una conexión a la base de datos. Toma como parámetros
* los valores de  las variables del entorno
* OPENSHIFT_MYSQL_DB_USERNAME y OPENSHIFT_MYSQL_DB_PASSWORD.
* La conexión se hace a través de c3p0, así que ya es un
* Pooled Data Source.
*/
public class DBConnection {

	private static ComboPooledDataSource cpds;

	static {
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass( "com.mysql.jdbc.Driver" );
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setJdbcUrl( "jdbc:mysql://localhost/fotocop");
		cpds.setUser(System.getenv("OPENSHIFT_MYSQL_DB_USERNAME"));
		cpds.setPassword(System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD"));
	}

	/**
	* Devuelve un objeto Connection listo para usar.
	*/
	public static Connection getConnection() throws SQLException {
		return cpds.getConnection();
	}

}
