package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBResourceManager {

/*	private static String JDBC_DRIVER = "net.sourceforge.jtds.jdbc.Driver";
	private static String JDBC_URL = "jdbc:jtds:sqlserver://127.0.0.1:1433;DatabaseName=db_horarios;namedPipes=true";
	private static String USER = "root";
	private static String PASSWORD = "root";*/
	
//	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	private static String JDBC_URL = "jdbc:mysql://mysql.agenciaprospect.com/agenciaprospec11";
//	private static String USER = "agenciaprospec11";
//	private static String PASSWORD = "prospect17";
	
	
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String JDBC_URL = "jdbc:mysql://localhost/bd_agendamentos";
	private static String USER = "root";
	private static String PASSWORD = "root";
	
	private static DBResourceManager instancia;
	
	private Connection con;
	 
	public Connection getCon(){
		return con;
	} 
	
	private DBResourceManager() throws ClassNotFoundException, SQLException{
		Class.forName(JDBC_DRIVER);
		con = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
	}
	
	public static DBResourceManager getInstanceConnection() throws ClassNotFoundException, SQLException{
		if (instancia == null) {
			instancia = new DBResourceManager();
		}
		return instancia;
	}
	
}


