package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.DistribuidorDAO;


/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo buscar en la base de datos
 * mediante el campo de clase correspondiente a todos los distribuidores que hay en el sistema
 * almacenados.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_Distribuidor {
	
	private DistribuidorDAO distribuidorDAO;
	

	BasicDataSource basicDataSource;

	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de distribuidor.
	 */
	public WS_Distribuidor() {
		

		
		basicDataSource = PoolConexiones.getInstance().getConnection();
		
		
		distribuidorDAO = new DistribuidorDAO(basicDataSource);
		
	}
	
	
	/**
	 * Funcion que consulta y obtiene de la base de datos todos los distribuidores que hay en el sistema, 
	 * mediante el objeto DAO que hay sido inicializado en el constructor por defecto.
	 * @return String que representa al xml con todos los distribuidores y toda la informacion de ellos.
	 */
	public String envioDistribuidores() {
		
		return distribuidorDAO.crearXML_Distribuidores(distribuidorDAO.obtenerDistribuidores());
		
	}

}
