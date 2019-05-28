package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.AlmacenDAO;


/**
 * Clase que representa a un WEB SERVICE que tiene como funcion obtener los articulos
 * que hay en el alamcen en un momento determinado
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_Almacen {

	private BasicDataSource basicDataSource;
	private AlmacenDAO almacenDAO;
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de almacen.
	 */
	public WS_Almacen() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		almacenDAO = new AlmacenDAO(basicDataSource);
	}
	
	/**
	 * Funcion que consulta todos los articulos que hay en el almacen, creando un xml con ellos
	 * y parseandolos a String para su envio mediante Web Service
	 * @return String que representa el xml con todos los articulo del almacen
	 */
	public String enviarAlmacen() {
		
		
		return almacenDAO.crearXML(almacenDAO.obtenAlmacen());
	}
}
