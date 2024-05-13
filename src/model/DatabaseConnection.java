package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	/*	importante poner '?noAccessToProcedureBodies=true' para evitar error de permisos.
	*	appuser solo puede hacer EXECUTE (osea CALL), pero si no pones '?noAccessToProcedureBodies=true', intenta leer
	* 	el contenido del PROCEDURE, pero no tiene permisos de lectura, por eso hay que poner '?noAccessToProcedureBodies=true'.
	* 
	* 	PD 2: Solucionado. Falta poner estos permisos GRANT al usuario:
	* 	'grant select on mysql.proc to 'appuser'@'%';' y tambien
	* 	'grant select on mysql.proc to 'appuser'@'localhost';'
	*/
	private static final String URL = "jdbc:mysql://localhost:3306/chat"; // Mi PC
	//private static final String URL = "jdbc:mysql://192.168.119.0:3306/chat"; // Toni PC
    private static final String USER = "appuser";
    private static final String PASSWORD = "TiC.123456";
    
    private static Connection connection;

    /**
     * Constructor privado por patron Singleton
     */
    private DatabaseConnection() {
        
    }

    
    /**
     * Metodo para conectar a la base de datos MySQL/MariaDB.
     * @return {@link Connection} El objeto de conexion.
     * @throws SQLException En caso de un error el la conexion.
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("No se puede clonar una clase Singleton");
    }
    
}