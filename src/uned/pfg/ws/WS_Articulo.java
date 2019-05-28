package uned.pfg.ws;

import org.apache.commons.dbcp.BasicDataSource;

import uned.pfg.dao.ArticuloDAO;



/**
 * Clase que representa a un WEB SERVICE que tiene como objetivo obtener todos los
 * articulos que hay en el sistema almacenados.
 *
 * @author JORGE VILLALBA RUIZ 47536486V
 * @version 1.0
 */
public class WS_Articulo {
	
	private ArticuloDAO articuloDAO;
	

	private BasicDataSource basicDataSource;

	
	
	/**
	 * Construtor por defecto, que llama al pool de conexiones ( si es que estaba ya creado, si no
	 * lo crea) y pasa este pool de conexiones al constructor del DAO de articulo.
	 */
	public WS_Articulo() {

		basicDataSource = PoolConexiones.getInstance().getConnection();
		articuloDAO = new ArticuloDAO(basicDataSource);
	}
	
	
	/**
	 * Funcion que, mediante el objeto DAO de articulo, selecciona todos los articulos que hay
	 * en el sistema, parseandolos a xml en una cadena de caracteres.
	 * @return String que representa al xml de todos los articulos que hay en el sistema.
	 */
	public String envioArticulos() {
		
		return articuloDAO.crearXML_Articulos(articuloDAO.getArticulos());
	}
}
