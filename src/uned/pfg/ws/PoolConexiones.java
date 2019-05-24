package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * Clase que crea el pool de conexiones a la base de datos. Implementa un
 * pantron Singleton con el fin de devolver siempre el mismo objeto una vez haya
 * sido creado por primera vez. Con esto conseguimos que siempre sea el mismo
 * pool el que sea utilizado por los objetos y no uno diferente en cada llamada
 * a esta clase.
 * 
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */

public class PoolConexiones {

	private static BasicDataSource basicDataSource;

	private static PoolConexiones instance;

	private PoolConexiones() {

		Conection();
	}

	/**
	 * Funcion que implementa el patron Singleton. Si la instancia de esta misma
	 * clase es igual a null, se crea el objeto llamando al constructor privado. En
	 * cambio, si no es null, devuelve la instancia que ya ha sido inicializada.
	 * 
	 * @return
	 */
	public static PoolConexiones getInstance() {

		if (instance == null) {

			instance = new PoolConexiones();

		}

		return instance;
	}

	/**
	 * Inicializa los valores del pool. Llama a la clase donde está el driver del
	 * conector jdbc de MySql, establece el usuario y contraseña de la base de
	 * datos, la url de conexion con la base de datos y el numero maximo de
	 * conexiones virtuales que tendrá el pool.
	 */
	private void Conection() {

		basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		basicDataSource.setUsername("root");
		basicDataSource.setPassword("");
		basicDataSource.setUrl("jdbc:mysql://localhost:3306/prueba_pfg");
		basicDataSource.setMaxActive(10);// 1000
		basicDataSource.setValidationQuery("select 1");
	}

	/**
	 * Funcion que devuelve el objeto inicializado del pool de conexiones
	 * 
	 * @return Objeto que representa al pool
	 */
	public BasicDataSource getConnection() {

		return basicDataSource;
	}
}
